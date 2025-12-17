package dev.runefox.blocktower.common.net

import dev.runefox.blocktower.common.model.Script
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerPlayer

object NetServer : Net() {
    fun sendOpenScript(to: ServerPlayer, script: ResourceKey<Script>) {
        ServerPlayNetworking.send(to, ShowScriptPayload(script))
    }
}
