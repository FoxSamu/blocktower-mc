package dev.runefox.blocktower.common.command

import dev.runefox.blocktower.common.map.SeatColor
import dev.runefox.blocktower.common.util.enumCodec
import net.minecraft.commands.arguments.StringRepresentableArgument

class SeatColorArgument : StringRepresentableArgument<SeatColor>(enumCodec(), { SeatColor.values() })

fun seatColor() = SeatColorArgument()
