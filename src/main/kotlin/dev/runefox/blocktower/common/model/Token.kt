package dev.runefox.blocktower.common.model

import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack

/**
 * A reminder token.
 */
data class Token(
    val icon: ItemStack,
    val name: Component,
)
