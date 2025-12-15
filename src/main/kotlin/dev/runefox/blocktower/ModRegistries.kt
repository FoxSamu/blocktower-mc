package dev.runefox.blocktower

import dev.runefox.blocktower.util.blocktower
import dev.runefox.blocktower.util.registry
import net.fabricmc.fabric.api.event.registry.DynamicRegistries

object ModRegistries {
    val ROLE = blocktower("role").registry<Role>()
    val SCRIPT = blocktower("script").registry<Script>()

    init {
        DynamicRegistries.registerSynced(ROLE, Role.DIRECT_CODEC)
        DynamicRegistries.registerSynced(SCRIPT, Script.DIRECT_CODEC)
    }
}
