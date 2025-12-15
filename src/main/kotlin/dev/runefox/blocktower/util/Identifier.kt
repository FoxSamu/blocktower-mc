package dev.runefox.blocktower.util

import net.minecraft.core.Registry
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey

fun minecraft(name: String): Identifier {
    return Identifier.withDefaultNamespace(name)
}

fun blocktower(name: String): Identifier {
    return Identifier.fromNamespaceAndPath("blocktower", name)
}

infix fun <T : Any> Identifier.of(reg: ResourceKey<out Registry<T>>): ResourceKey<T> {
    return ResourceKey.create(reg, this)
}

infix fun <T : Any> Identifier.of(reg: Registry<T>): ResourceKey<T> {
    return this of reg.key()
}


fun <T : Any> Identifier.registry(): ResourceKey<out Registry<T>> {
    return ResourceKey.createRegistryKey(this)
}
