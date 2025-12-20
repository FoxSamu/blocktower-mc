package dev.runefox.blocktower.common.game

import dev.runefox.blocktower.common.util.*
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties.*

enum class VoteState(
    val voting: Boolean,
    val canVote: Boolean,
    val lamp: BlockState,
    val base: BlockState
) {
    ALIVE(false, true, Blocks.REDSTONE_LAMP.default, Blocks.GOLD_BLOCK.default),
    ALIVE_VOTE(true, true, Blocks.REDSTONE_LAMP.default.setTrue(LIT), Blocks.GOLD_BLOCK.default),
    GHOST(false, true, Blocks.WAXED_OXIDIZED_COPPER.default, Blocks.IRON_BLOCK.default),
    GHOST_VOTE(true, true, Blocks.SEA_LANTERN.default, Blocks.IRON_BLOCK.default),
    DEAD(false, false, Blocks.BLACK_CONCRETE.default, Blocks.NETHERITE_BLOCK.default),
}
