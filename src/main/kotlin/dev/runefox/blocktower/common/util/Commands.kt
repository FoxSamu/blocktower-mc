package dev.runefox.blocktower.common.util

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.LiteralMessage
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType
import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.exceptions.DynamicNCommandExceptionType
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.mojang.brigadier.tree.CommandNode
import com.mojang.brigadier.tree.LiteralCommandNode
import com.mojang.serialization.Codec
import dev.runefox.blocktower.common.Mod
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.StringRepresentableArgument
import net.minecraft.network.chat.Component
import java.util.function.Supplier
import kotlin.jvm.java
import kotlin.reflect.KClass

fun literal(literal: String) {
    val lit = Commands.literal("")
}

inline fun <S> CommandDispatcher<S>.command(name: String, builder: LiteralCommandBuilder<S>.() -> Unit): LiteralCommandNode<S> {
    return register(CommandBuilder(LiteralArgumentBuilder.literal<S>(name)).apply { builder() }.argument)
}

operator fun <T> CommandContext<*>.get(ref: ArgumentRef<T, *>): T {
    return getArgument(ref.name, ref.type.java)
}

operator fun <T> ArgumentType<T>.get(name: String): ArgumentDef<T> {
    return ArgumentDef(name, this)
}

typealias LiteralCommandBuilder<S> = CommandBuilder<S, LiteralArgumentBuilder<S>>
typealias ArgumentCommandBuilder<S, T> = CommandBuilder<S, RequiredArgumentBuilder<S, T>>

typealias GameCommandContext = CommandContext<CommandSourceStack>
typealias GameCommandBuilder = CommandBuilder<CommandSourceStack, *>

class CommandBuilder<S, A : ArgumentBuilder<S, *>>(val argument: A) {

    inline operator fun String.invoke(
        builder: LiteralCommandBuilder<S>.() -> Unit
    ): LiteralArgumentBuilder<S> {
        return LiteralArgumentBuilder.literal<S>(this).invoke(builder)
    }

    inline operator fun <T, reified U : T & Any> ArgumentDef<T>.invoke(
        builder: ArgumentCommandBuilder<S, T>.(ArgumentRef<T, U>) -> Unit
    ): RequiredArgumentBuilder<S, T> {
        val ref = ArgumentRef<T, U>(name, U::class)
        return RequiredArgumentBuilder.argument<S, T>(name, type).invoke { builder(ref) }
    }

    inline operator fun <A : ArgumentBuilder<S, *>> A.invoke(builder: CommandBuilder<S, A>.() -> Unit = {}): A {
        argument.then(CommandBuilder(this).apply(builder).argument)
        return this
    }

    inline fun onExecute(crossinline command: CommandContext<S>.(S) -> Int) {
        argument.executes {
            try {
                it.command(it.source)
            } catch (e: CommandSyntaxException) {
                throw e
            } catch (e: Throwable) {
                Mod.LOGGER.error("Exception during command", e)
                0
            }
        }
    }

    inline fun require(crossinline predicate: (S) -> Boolean) {
        argument.requires { predicate(it) }
    }

    fun redirect(node: CommandNode<S>) {
        argument.redirect(node)
    }
}

class ArgumentDef<T>(val name: String, val type: ArgumentType<T>)
class ArgumentRef<T, U : T & Any>(val name: String, val type: KClass<U>)

fun Formattable.simpleErrorType() = SimpleCommandExceptionType(format())
fun Formattable.dynamicErrorType1() = DynamicCommandExceptionType { format(it) }
fun Formattable.dynamicErrorType2() = Dynamic2CommandExceptionType { a, b -> format(a, b) }
fun Formattable.dynamicErrorType3() = Dynamic3CommandExceptionType { a, b, c -> format(a, b, c) }
fun Formattable.dynamicErrorType4() = Dynamic4CommandExceptionType { a, b, c, d -> format(a, b, c, d) }
fun Formattable.dynamicErrorTypeN() = DynamicNCommandExceptionType { format(*it) }

fun commandError(message: String): Nothing {
    throw SimpleCommandExceptionType(LiteralMessage(message)).create()
}

fun commandError(message: Component): Nothing {
    throw SimpleCommandExceptionType(message).create()
}

fun commandError(message: Formattable): Nothing {
    throw message.simpleErrorType().create()
}

fun commandError(message: Formattable, a: Any?): Nothing {
    throw message.dynamicErrorType1().create(a)
}

fun commandError(message: Formattable, a: Any?, b: Any?): Nothing {
    throw message.dynamicErrorType2().create(a, b)
}

fun commandError(message: Formattable, a: Any?, b: Any?, c: Any?): Nothing {
    throw message.dynamicErrorType3().create(a, b, c)
}

fun commandError(message: Formattable, a: Any?, b: Any?, c: Any?, d: Any?): Nothing {
    throw message.dynamicErrorType4().create(a, b, c, d)
}

fun commandError(message: Formattable, vararg args: Any?): Nothing {
    // For some reason this create method requires some random unused parameter
    throw message.dynamicErrorTypeN().create(null, *args)
}

fun GameCommandContext.failure(message: Formattable, a: Any?, vararg args: Any?) {
    source.sendFailure(message.format(a, *args))
}

fun GameCommandContext.failure(message: Formattable) {
    source.sendFailure(message.format())
}

fun GameCommandContext.failure(message: Component) {
    source.sendFailure(message)
}

fun GameCommandContext.success(message: Formattable, a: Any?, vararg args: Any?, broadcast: Boolean = false) {
    source.sendSuccess({ message.format(a, *args) }, broadcast)
}

fun GameCommandContext.success(message: Formattable, broadcast: Boolean = false) {
    source.sendSuccess({ message.format() }, broadcast)
}

fun GameCommandContext.success(message: Component, broadcast: Boolean = false) {
    source.sendSuccess({ message }, broadcast)
}
