package dev.runefox.blocktower.common.util

import dev.runefox.blocktower.common.Mod
import net.minecraft.core.Registry
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import kotlin.reflect.KProperty

abstract class DynamicDataHolder<E : Any>(
    val registryKey: ResourceKey<out Registry<E>>,
    val namespace: String = Mod.ID
) {
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun getValue(rec: Any?, prop: KProperty<*>): ResourceKey<E> {
        return Identifier.fromNamespaceAndPath(namespace, prop.name.lowercase()) of registryKey
    }

    abstract fun register(entries: BootstrapContext<E>)
}
