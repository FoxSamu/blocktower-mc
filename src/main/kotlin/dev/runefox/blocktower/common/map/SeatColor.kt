package dev.runefox.blocktower.common.map

import dev.runefox.blocktower.common.util.MapStyle
import dev.runefox.blocktower.common.util.SerialEnum
import dev.runefox.blocktower.common.util.T
import net.minecraft.util.StringRepresentable
import net.minecraft.world.item.DyeColor

enum class SeatColor(override val serialName: String, val dyeColor: DyeColor) : SerialEnum {
    RED("red", DyeColor.RED),
    ORANGE("orange", DyeColor.ORANGE),
    YELLOW("yellow", DyeColor.YELLOW),
    LIME("lime", DyeColor.LIME),
    GREEN("green", DyeColor.GREEN),
    CYAN("cyan", DyeColor.CYAN),
    LIGHT_BLUE("light_blue", DyeColor.LIGHT_BLUE),
    BLUE("blue", DyeColor.BLUE),
    PURPLE("purple", DyeColor.PURPLE),
    MAGENTA("magenta", DyeColor.MAGENTA),
    PINK("pink", DyeColor.PINK),
    WHITE("white", DyeColor.WHITE),
    LIGHT_GRAY("light_gray", DyeColor.LIGHT_GRAY),
    GRAY("gray", DyeColor.GRAY),
    BROWN("brown", DyeColor.BROWN),
    BLACK("black", DyeColor.BLACK);

    val style = MapStyle { it.withColor(dyeColor.textColor) }
    val displayName = T(serialName, style)

    companion object {
        private val byName = StringRepresentable.createNameLookup(values())

        operator fun get(name: String): SeatColor? {
            return byName.apply(name)
        }
    }
}
