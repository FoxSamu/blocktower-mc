package dev.runefox.blocktower.common.model

import dev.runefox.blocktower.common.util.fieldOf
import dev.runefox.blocktower.common.util.forGetter
import dev.runefox.blocktower.common.util.recordCodec
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentSerialization
import net.minecraft.world.item.ItemStack

/**
 * A reminder token.
 */
data class Token(
    val icon: ItemStack,
    val name: Component,
) {
    companion object {
        val CODEC = recordCodec<Token> {
            group(
                ItemStack.CODEC fieldOf "icon" forGetter { icon },
                ComponentSerialization.CODEC fieldOf "name" forGetter { name }
            ).apply(this, ::Token)
        }
    }
}
