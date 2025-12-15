package dev.runefox.blocktower.util

import com.mojang.datafixers.util.Pair
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.ListBuilder
import com.mojang.serialization.RecordBuilder
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.CompositeDecoder.Companion.UNKNOWN_NAME
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.modules.SerializersModule

class Dfu(
    val serializersModule: SerializersModule
) {
    fun <T, E> encode(element: E, encoder: SerializationStrategy<E>, ops: DynamicOps<T>): DataResult<T> {
        return try {
            DfuRootEncoder(this, ops).let {
                it.encodeSerializableValue(encoder, element)
                it.get()
            }
        } catch (e: DataResultErrorException) {
            e.result.flatMap { DataResult.error { "Error but there is no error" } }
        }
//        catch (e: Exception) {
//            DataResult.error { e.toString() }
//        }
    }
}

interface DfuEncoder<T> : Encoder {
    val dfu: Dfu
    val ops: DynamicOps<T>

    fun encodeRaw(raw: T)
    fun encodeRaw(raw: DataResult<T>)

    override val serializersModule: SerializersModule
        get() = dfu.serializersModule
}

interface DfuDecoder<T> : Decoder {
    val dfu: Dfu
    val ops: DynamicOps<T>

    fun decodeRaw(): T

    override val serializersModule: SerializersModule
        get() = dfu.serializersModule
}

@OptIn(ExperimentalSerializationApi::class)
private sealed class DfuEncoderImpl<T>(
    override val dfu: Dfu,
    override val ops: DynamicOps<T>
) : AbstractEncoder(), DfuEncoder<T> {
    abstract fun get(): DataResult<T>

    override fun encodeRaw(raw: T) {
        encodeRaw(DataResult.success(raw))
    }

    override fun encodeNull() {
        encodeRaw(ops.empty())
    }

    override fun encodeBoolean(value: Boolean) {
        encodeRaw(ops.createBoolean(value))
    }

    override fun encodeByte(value: Byte) {
        encodeRaw(ops.createByte(value))
    }

    override fun encodeShort(value: Short) {
        encodeRaw(ops.createShort(value))
    }

    override fun encodeChar(value: Char) {
        encodeRaw(ops.createString("$value"))
    }

    override fun encodeInt(value: Int) {
        encodeRaw(ops.createInt(value))
    }

    override fun encodeLong(value: Long) {
        encodeRaw(ops.createLong(value))
    }

    override fun encodeFloat(value: Float) {
        encodeRaw(ops.createFloat(value))
    }

    override fun encodeDouble(value: Double) {
        encodeRaw(ops.createDouble(value))
    }

    override fun encodeString(value: String) {
        encodeRaw(ops.createString(value))
    }

    override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) {
        encodeRaw(ops.createString(enumDescriptor.getElementName(index)))
    }

    override fun encodeInline(descriptor: SerialDescriptor): Encoder {
        return this
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        return DfuMapEncoder(dfu, ops, this)
    }

    override fun beginCollection(descriptor: SerialDescriptor, collectionSize: Int): CompositeEncoder {
        return DfuListEncoder(dfu, ops, this)
    }
}

private class DfuRootEncoder<T>(dfu: Dfu, ops: DynamicOps<T>) : DfuEncoderImpl<T>(dfu, ops) {
    var value: DataResult<T> = DataResult.error { "No value encoded" }

    override fun encodeRaw(raw: DataResult<T>) {
        this.value = raw
    }

    override fun get(): DataResult<T> {
        return value
    }
}

private class DfuListEncoder<T>(
    dfu: Dfu,
    ops: DynamicOps<T>,
    val parent: DfuEncoderImpl<T>
) : DfuEncoderImpl<T>(dfu, ops) {
    private var list: ListBuilder<T> = ops.listBuilder()

    override fun encodeRaw(raw: DataResult<T>) {
        list.add(raw)
    }

    override fun get(): DataResult<T> {
        return list.build(ops.emptyList())
    }

    override fun endStructure(descriptor: SerialDescriptor) {
        parent.encodeRaw(get())
    }
}

private class DfuMapEncoder<T>(
    dfu: Dfu,
    ops: DynamicOps<T>,
    val parent: DfuEncoderImpl<T>
) : DfuEncoderImpl<T>(dfu, ops) {
    private var map: RecordBuilder<T> = ops.mapBuilder()

    private val missingTag: DataResult<T> = DataResult.error { "No tag has been seen, use encode...Element" }
    private var currentTag: DataResult<T> = missingTag

    override fun encodeRaw(raw: DataResult<T>) {
        map.add(currentTag, raw)
        currentTag = missingTag
    }

    override fun get(): DataResult<T> {
        return map.build(ops.emptyMap())
    }

    override fun encodeElement(descriptor: SerialDescriptor, index: Int): Boolean {
        currentTag = DataResult.success(ops.createString(descriptor.getElementName(index)))
        return true
    }

    override fun endStructure(descriptor: SerialDescriptor) {
        parent.encodeRaw(get())
    }
}

@OptIn(ExperimentalSerializationApi::class)
private sealed class DfuDecoderImpl<T>(
    override val dfu: Dfu,
    override val ops: DynamicOps<T>
) : AbstractDecoder(), DfuDecoder<T> {
    override fun decodeRaw(): T {
        return decode()
    }

    protected abstract fun decode(): T

    abstract override fun decodeNotNullMark(): Boolean

    private inline fun <E> decode(decode: (T) -> DataResult<E>): E {
        val decode = decode(decode())
        if (decode.isError) {
            throw DataResultErrorException(decode)
        }

        return decode.result().orElseThrow()
    }

    override fun decodeBoolean(): Boolean {
        return decode { ops.getBooleanValue(it) }
    }

    override fun decodeByte(): Byte {
        return decode { ops.getNumberValue(it) }.toByte()
    }

    override fun decodeChar(): Char {
        return decode {
            ops.getStringValue(it).flatMap { str ->
                if (str.length == 1) {
                    DataResult.success(str[0])
                } else {
                    DataResult.error { "Not a character: '$str'" }
                }
            }
        }
    }

    override fun decodeDouble(): Double {
        return decode { ops.getNumberValue(it) }.toDouble()
    }

    override fun decodeEnum(enumDescriptor: SerialDescriptor): Int {
        return decode {
            ops.getStringValue(it).flatMap { str ->
                val index = enumDescriptor.getElementIndex(str)
                if (index != UNKNOWN_NAME) {
                    DataResult.success(index)
                } else {
                    DataResult.error { "Not a character: '$str'" }
                }
            }
        }
    }

    override fun decodeFloat(): Float {
        return decode { ops.getNumberValue(it) }.toFloat()
    }

    override fun decodeInt(): Int {
        return decode { ops.getNumberValue(it) }.toInt()
    }

    override fun decodeLong(): Long {
        return decode { ops.getNumberValue(it) }.toLong()
    }

    override fun decodeNull(): Nothing? {
        return decode {
            if (it == ops.empty()) {
                DataResult.success(null)
            } else {
                DataResult.error { "Not a null value: $it" }
            }
        }
    }

    override fun decodeShort(): Short {
        return decode { ops.getNumberValue(it) }.toShort()
    }

    override fun decodeString(): String {
        return decode { ops.getStringValue(it) }
    }
}

private class DfuRootDecoder<T>(
    dfu: Dfu,
    ops: DynamicOps<T>,
    val value: T
) : DfuDecoderImpl<T>(dfu, ops) {
    override fun decode(): T {
        return value
    }

    override fun decodeNotNullMark(): Boolean {
        return value != ops.empty()
    }

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        throw DataResultErrorException(DataResult.error<Nothing> { "Cannot query root value as object" })
    }
}

private class DfuListDecoder<T>(
    dfu: Dfu,
    ops: DynamicOps<T>,
    val elements: List<T>
) : DfuDecoderImpl<T>(dfu, ops) {
    private var index = 0

    override fun decode(): T {
        return elements[index ++]
    }

    override fun decodeNotNullMark(): Boolean {
        return elements[index] != ops.empty()
    }

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        throw DataResultErrorException(DataResult.error<Nothing> { "Cannot query list as object" })
    }
}

private class DfuMapDecoder<T>(
    dfu: Dfu,
    ops: DynamicOps<T>,
    val pairs: Iterator<Pair<T, T>>
) : DfuDecoderImpl<T>(dfu, ops) {
    private lateinit var cur: Pair<T, T>

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        return if (pairs.hasNext()) {
            cur = pairs.next()

            val result = ops.getStringValue(cur.first)
            if (result.isError) {
                throw DataResultErrorException(result)
            } else {
                descriptor.getElementIndex(result.result().orElseThrow())
            }
        } else {
            DECODE_DONE
        }
    }

    override fun decode(): T {
        return cur.second
    }

    override fun decodeNotNullMark(): Boolean {
        return cur.second != ops.empty()
    }
}

class DataResultErrorException(val result: DataResult<*>) : Exception("$result", null, false, false)
