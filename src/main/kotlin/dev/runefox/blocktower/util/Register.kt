package dev.runefox.blocktower.util

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey

fun <T : Any> Registry<T>.register(name: ResourceKey<T>, element: T): T {
    return Registry.register(this, name, element)
}

