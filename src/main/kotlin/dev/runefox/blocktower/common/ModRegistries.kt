package dev.runefox.blocktower.common

import dev.runefox.blocktower.common.model.Fabled
import dev.runefox.blocktower.common.model.Role
import dev.runefox.blocktower.common.model.Loric
import dev.runefox.blocktower.common.model.Script
import dev.runefox.blocktower.common.util.blocktower
import dev.runefox.blocktower.common.util.registry
import net.fabricmc.fabric.api.event.registry.DynamicRegistries

object ModRegistries {
    val ROLE = blocktower("role").registry<Role>()
    val FABLED = blocktower("fabled").registry<Fabled>()
    val LORIC = blocktower("loric").registry<Loric>()
    val SCRIPT = blocktower("script").registry<Script>()

    init {
        DynamicRegistries.registerSynced(ROLE, Codecs.Role)
        DynamicRegistries.registerSynced(FABLED, Codecs.Fabled)
        DynamicRegistries.registerSynced(LORIC, Codecs.Loric)
        DynamicRegistries.registerSynced(SCRIPT, Codecs.Script)
    }
}
