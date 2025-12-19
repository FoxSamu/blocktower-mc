package dev.runefox.blocktower.common

import dev.runefox.blocktower.common.model.Script
import dev.runefox.blocktower.common.model.script
import dev.runefox.blocktower.common.util.DynamicDataHolder
import net.minecraft.data.worldgen.BootstrapContext

object ModScripts : DynamicDataHolder<Script>(ModRegistries.SCRIPT) {
    val TROUBLE_BREWING by this

    override fun register(entries: BootstrapContext<Script>) {
        val roles = entries.lookup(ModRegistries.ROLE)
        entries.register(TROUBLE_BREWING, script(roles) { troubleBrewing() })
    }
}
