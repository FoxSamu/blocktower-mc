package dev.runefox.blocktower.common.model

import dev.runefox.blocktower.common.util.MapStyle
import dev.runefox.blocktower.common.util.T
import dev.runefox.blocktower.common.util.plus
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component

interface Described {
    val name: Component
    val description: Component
    val lore: Component?

    val wikiUrl: String?
}

fun Described.buildFullDescription(loreFirst: Boolean = false): Component {
    val lore = lore ?: return description

    return if (loreFirst) {
        T(lore + LoreStyle, "\n\n", description)
    } else {
        T(description, "\n\n", lore + LoreStyle)
    }
}

val LoreStyle = MapStyle {
    it.withItalic(true).withColor(ChatFormatting.GRAY)
}
