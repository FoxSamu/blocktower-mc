package dev.runefox.blocktower

import dev.runefox.blocktower.util.of
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider
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
