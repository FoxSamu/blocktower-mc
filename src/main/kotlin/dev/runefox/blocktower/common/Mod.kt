package dev.runefox.blocktower.common

import dev.runefox.blocktower.common.net.NetServer
import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object Mod : ModInitializer {
    const val ID = "blocktower"
    val LOGGER: Logger = LogManager.getLogger(ID)

    override fun onInitialize() {
        ModItems
        ModRegistries
        NetServer
    }
}
