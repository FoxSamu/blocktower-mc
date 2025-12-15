package dev.runefox.blocktower

import com.mojang.serialization.Lifecycle
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.JsonKeySortOrderCallback
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.client.data.models.BlockModelGenerators
import net.minecraft.client.data.models.ItemModelGenerators
import net.minecraft.client.data.models.model.ModelTemplates
import net.minecraft.core.Holder
import net.minecraft.core.HolderGetter
import net.minecraft.core.HolderLookup
import net.minecraft.core.Registry
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import java.util.concurrent.CompletableFuture

object Datagen : DataGeneratorEntrypoint {
    val ROLE_ITEMS = mutableListOf<Item>()
    val TRANSLATIONS = mutableMapOf<String, String>()

    override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
        gen.createPack().apply {
            addProvider(::Models)
            addProvider(::EnUsLang)
            addProvider(::Regs)
        }
    }

    override fun buildRegistry(registryBuilder: RegistrySetBuilder) {
        registryBuilder.add(ModRegistries.ROLE) { ModRoles.register(it) }
        registryBuilder.add(ModRegistries.SCRIPT) { ModScripts.register(it) }
    }

    override fun addJsonKeySortOrders(callback: JsonKeySortOrderCallback) {
        callback.add("type", -7)
        callback.add("name", -6)
        callback.add("role", -5)
        callback.add("description", -4)
        callback.add("lore", -3)
        callback.add("condition", -2)
        callback.add("icon", -1)
    }
}

private class Models(output: FabricDataOutput) : FabricModelProvider(output) {
    override fun generateBlockStateModels(gen: BlockModelGenerators) {
    }

    override fun generateItemModels(gen: ItemModelGenerators) {
        for (item in Datagen.ROLE_ITEMS) {
            gen.generateFlatItem(item, ModelTemplates.FLAT_ITEM)
        }
    }
}

private class EnUsLang(output: FabricDataOutput, regs: CompletableFuture<HolderLookup.Provider>) : FabricLanguageProvider(output, regs) {
    override fun generateTranslations(regs: HolderLookup.Provider, output: TranslationBuilder) {
        for ((k, v) in Datagen.TRANSLATIONS) {
            output.add(k, v)
        }
    }
}

private class Regs(output: FabricDataOutput, regs: CompletableFuture<HolderLookup.Provider>) : FabricDynamicRegistryProvider(output, regs) {
    override fun configure(lookup: HolderLookup.Provider, entries: Entries) {
        class Ctx<T : Any> : BootstrapContext<T> {
            override fun register(key: ResourceKey<T>, v: T, lifecycle: Lifecycle): Holder.Reference<T> {
                return entries.add(key, v) as Holder.Reference<T>
            }

            override fun <S : Any> lookup(key: ResourceKey<out Registry<out S>>): HolderGetter<S> {
                return entries.lookups.lookupOrThrow(key)
            }
        }

        ModRoles.register(Ctx())
        ModScripts.register(Ctx())
    }

    override fun getName(): String {
        return "Regs"
    }
}
