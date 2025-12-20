package dev.runefox.blocktower.common.util

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.Property

val Block.default: BlockState get() = defaultBlockState()

fun BlockState.setTrue(prop: Property<Boolean>): BlockState {
    return setValue(prop, true)
}

fun BlockState.setFalse(prop: Property<Boolean>): BlockState {
    return setValue(prop, false)
}
