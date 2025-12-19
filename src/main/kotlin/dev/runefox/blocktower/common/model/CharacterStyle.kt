package dev.runefox.blocktower.common.model

import net.minecraft.ChatFormatting

enum class CharacterStyle(
    val textStyle: ChatFormatting
) {
    GOOD(ChatFormatting.AQUA),
    EVIL(ChatFormatting.RED),
    FABLED(ChatFormatting.GOLD),
    LORIC(ChatFormatting.GREEN)
}
