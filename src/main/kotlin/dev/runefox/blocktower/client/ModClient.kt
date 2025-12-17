package dev.runefox.blocktower.client

import dev.runefox.blocktower.client.gui.GuiTexts
import dev.runefox.blocktower.client.net.NetClient
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Environment(EnvType.CLIENT)
object ModClient : ClientModInitializer {
    override fun onInitializeClient() {
        GuiTexts
        NetClient
    }
}
