package dev.runefox.blocktower.common.util

import java.util.Optional
import com.mojang.datafixers.kinds.App
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.UnboundedMapCodec
import net.minecraft.core.Holder
import net.minecraft.core.HolderSet
import net.minecraft.core.Registry
import net.minecraft.core.RegistryCodecs
import net.minecraft.resources.RegistryFileCodec
import net.minecraft.resources.ResourceKey
import net.minecraft.util.StringRepresentable

inline fun <reified E> enumCodec(): Codec<E> where E : Enum<E>, E : StringRepresentable {
    return StringRepresentable.fromEnum { E::class.java.enumConstants }
}

inline fun <E> recordMapCodec(crossinline builder: RecordCodecBuilder.Instance<E>.() -> App<RecordCodecBuilder.Mu<E>, E>): MapCodec<E> {
    return RecordCodecBuilder.mapCodec { it.builder() }
}

inline fun <E> recordCodec(crossinline builder: RecordCodecBuilder.Instance<E>.() -> App<RecordCodecBuilder.Mu<E>, E>): Codec<E> {
    return RecordCodecBuilder.create { it.builder() }
}

fun <E : Any> Codec<E>.holder(registry: ResourceKey<out Registry<E>>, allowInline: Boolean = true): Codec<Holder<E>> {
    return RegistryFileCodec.create(registry, this, allowInline)
}

fun <E : Any> Codec<E>.holderSet(registry: ResourceKey<out Registry<E>>, allowInline: Boolean = true): Codec<HolderSet<E>> {
    return RegistryCodecs.homogeneousList(registry, this, allowInline)
}


infix fun <E> Codec<E>.fieldOf(name: String): MapCodec<E> {
    return fieldOf(name)
}

infix fun <E : Any> Codec<E>.fieldOf(name: OptionalField): MapCodec<Optional<E>> {
    return optionalFieldOf(name.name)
}

infix fun <E : Any> Codec<E>.fieldOf(name: OptionalFieldWithFallback<E>): MapCodec<E> {
    return optionalFieldOf(name.name, name.fallback)
}

inline infix fun <E, O> MapCodec<E>.forGetter(crossinline getter: O.() -> E): RecordCodecBuilder<O, E> {
    return forGetter { getter(it) }
}

inline infix fun <E, O> MapCodec<Optional<E>>.forNullGetter(crossinline getter: O.() -> E?): RecordCodecBuilder<O, Optional<E>> {
    @Suppress("unchecked_cast")
    return forGetter { Optional.ofNullable(getter(it)) as Optional<E> }
}

fun optional(name: String) = OptionalField(name)
fun <E> optional(name: String, fallback: E) = OptionalFieldWithFallback(name, fallback)

infix fun <K, V> Codec<K>.mapping(v: Codec<V>): UnboundedMapCodec<K, V> {
    return Codec.unboundedMap(this, v)
}

val <E> Codec<E>.list: Codec<List<E>> get() = listOf()
val <E> Codec<E>.set: Codec<Set<E>> get() = list.xmap(List<E>::toSet, Set<E>::toList)


class OptionalField(
    val name: String
)

class OptionalFieldWithFallback<out E>(
    val name: String,
    val fallback: E
)
