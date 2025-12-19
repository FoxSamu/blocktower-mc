package dev.runefox.blocktower.common.map

import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component

data class TownSeat(
    // TODO include dimension
    val seatPos: BlockPos,
    val bedPos: BlockPos,
    val voteLeverPos: BlockPos,
    val voteLampPos: BlockPos,
    val houseName: Component
)
