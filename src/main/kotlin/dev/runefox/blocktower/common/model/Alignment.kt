package dev.runefox.blocktower.common.model

import dev.runefox.blocktower.common.util.SerialEnum
import net.minecraft.ChatFormatting

enum class Alignment(
    override val serialName: String,
    val style: CharacterStyle
) : SerialEnum {
    GOOD("good", CharacterStyle.GOOD),
    EVIL("evil", CharacterStyle.EVIL)
}
