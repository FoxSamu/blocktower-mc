package dev.runefox.blocktower.common.item

import dev.runefox.blocktower.common.ModScripts
import dev.runefox.blocktower.common.net.NetServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level

/**
 * An item tied to a script. Clicking this item anywhere will open a screen with the script ID stored in its `blocktower:script` component.
 */
class ScriptItem(properties: Properties) : Item(properties) {
    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResult {
        if (!level.isClientSide && player is ServerPlayer) {
            // TODO script item component
            NetServer.sendOpenScript(player, ModScripts.TROUBLE_BREWING)
        }

        return InteractionResult.CONSUME
    }
}
