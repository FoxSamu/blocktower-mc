package dev.runefox.blocktower.common.map

import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.Level

data class Location(
    val dimension: ResourceKey<Level>,
    val pos: BlockPos
)
