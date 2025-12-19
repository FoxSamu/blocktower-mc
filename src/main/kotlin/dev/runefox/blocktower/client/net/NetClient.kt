package dev.runefox.blocktower.client.net

import dev.runefox.blocktower.client.gui.ScriptScreen
import dev.runefox.blocktower.common.net.Net
import dev.runefox.blocktower.common.net.ShowScriptPayload
import dev.runefox.blocktower.client.util.display
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking

@Environment(EnvType.CLIENT)
object NetClient : Net() {
    init {
        ClientPlayNetworking.registerGlobalReceiver(ShowScriptPayload.TYPE, ::showScript)
    }

    private fun showScript(payload: ShowScriptPayload, context: ClientPlayNetworking.Context) {
        val holder = context.player().registryAccess().getOrThrow(payload.script)
        ScriptScreen(holder.value()).display()
    }
}
