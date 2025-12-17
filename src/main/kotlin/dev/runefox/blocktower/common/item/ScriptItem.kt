package dev.runefox.blocktower.common.item

import dev.runefox.blocktower.common.ModScripts
import dev.runefox.blocktower.common.net.ShowScriptPayload
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level

class ScriptItem(properties: Properties) : Item(properties) {
    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResult {
        if (!level.isClientSide && player is ServerPlayer) {
            ServerPlayNetworking.send(player, ShowScriptPayload(ModScripts.TROUBLE_BREWING))
        }

        return InteractionResult.SUCCESS
    }
}
