package dev.runefox.blocktower.common

import dev.runefox.blocktower.common.model.Role
import dev.runefox.blocktower.common.model.Script
import dev.runefox.blocktower.common.util.blocktower
import dev.runefox.blocktower.common.util.registry
import net.fabricmc.fabric.api.event.registry.DynamicRegistries

object ModRegistries {
    val ROLE = blocktower("role").registry<Role>()
    val SCRIPT = blocktower("script").registry<Script>()

    init {
        DynamicRegistries.registerSynced(ROLE, Role.DIRECT_CODEC)
        DynamicRegistries.registerSynced(SCRIPT, Script.DIRECT_CODEC)
    }
}
