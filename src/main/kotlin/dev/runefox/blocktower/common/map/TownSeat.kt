package dev.runefox.blocktower.common.map

import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.level.Level

data class TownSeat(
    val seatPos: Location,
    val bedPos: Location,
    val voteLeverPos: Location,
    val voteLampPos: Location,
    val houseName: Component
) {
    constructor() : this(
        Location(Level.OVERWORLD, BlockPos(0, 0, 0)),
        Location(Level.OVERWORLD, BlockPos(0, 0, 0)),
        Location(Level.OVERWORLD, BlockPos(0, 0, 0)),
        Location(Level.OVERWORLD, BlockPos(0, 0, 0)),
        Component.empty()
    )
}
