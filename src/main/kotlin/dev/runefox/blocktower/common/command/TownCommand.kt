package dev.runefox.blocktower.common.command

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType.*
import dev.runefox.blocktower.common.map.Location
import dev.runefox.blocktower.common.map.SeatColor
import dev.runefox.blocktower.common.map.TownSeat
import dev.runefox.blocktower.common.util.*
import net.minecraft.commands.arguments.DimensionArgument.dimension
import net.minecraft.commands.arguments.coordinates.BlockPosArgument.blockPos
import net.minecraft.commands.arguments.coordinates.Coordinates
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.server.permissions.Permissions

fun GameCommandBuilder.town() = "town" {
    require { it.permissions().hasPermission(Permissions.COMMANDS_ADMIN) }

    "init" {
        onExecute { init() }
    }

    "delete" {
        onExecute { delete() }
    }

    "center" {
        onExecute { center() }

        blockPos()["pos"] { pos ->
            onExecute { center(this[pos]) }

            dimension()["dimension"] { dimension ->
                onExecute { center(this[pos], this[dimension]) }
            }
        }
    }

    "seats" {
        onExecute { seats() }

        "clear" {
            onExecute { seatsClear() }
        }

        "add" {
            onExecute { seatsAdd() }

            seatColor()["color"] { color ->
                onExecute { seatsAdd(this[color]) }
            }
        }

        "remove" {
            seatColor()["color"] { color ->
                onExecute { seatsRemove(this[color]) }
            }
        }

        "alloc" {
            integer(0, 16)["size"] { size -> onExecute { seatsAlloc(this[size], 0) } }

            "atmost" {
                integer(0, 16)["size"] { size -> onExecute { seatsAlloc(this[size], 1) } }
            }

            "atleast" {
                integer(0, 16)["size"] { size -> onExecute { seatsAlloc(this[size], 2) } }
            }
        }
    }
}

private fun GameCommandContext.init(): Int {
    val server = source.server

    val town = server.town

    if (town != null) {
        commandError(CommandMessages.Town.ALREADY_HAS_TOWN)
    }

    server.initTown()

    success(CommandMessages.Town.INITIALIZED)
    return 1
}

private fun GameCommandContext.delete(): Int {
    val server = source.server

    if (!server.deleteTown()) {
        commandError(CommandMessages.Town.HAS_NO_TOWN)
    }

    success(CommandMessages.Town.DELETED)
    return 1
}

private fun GameCommandContext.center(): Int {
    val town = source.server.town ?: commandError(CommandMessages.Town.HAS_NO_TOWN)

    success(CommandMessages.Town.GET_CENTER, CommandMessages.formatLocation(town.center))
    return 1
}

private fun GameCommandContext.center(pos: Coordinates): Int {
    val pos = pos.getBlockPos(source)

    val town = source.server.town ?: commandError(CommandMessages.Town.HAS_NO_TOWN)
    town.center = Location(source.level.dimension(), pos)

    success(CommandMessages.Town.SET_CENTER, CommandMessages.formatLocation(town.center))
    return 1
}

private fun GameCommandContext.center(pos: Coordinates, dimen: Identifier): Int {
    val pos = pos.getBlockPos(source)

    val town = source.server.town ?: commandError(CommandMessages.Town.HAS_NO_TOWN)
    town.center = Location(ResourceKey.create(Registries.DIMENSION, dimen), pos)

    success(CommandMessages.Town.SET_CENTER, CommandMessages.formatLocation(town.center))
    return 1
}

private fun GameCommandContext.seats(): Int {
    val town = source.server.town ?: commandError(CommandMessages.Town.HAS_NO_TOWN)

    val seats = town.seats()

    val message = CommandMessages.Town.SEAT_LIST.format(seats.size).copy()
    for (col in seats.keys) {
        message.append("\n")
        message.append(CommandMessages.Town.SEAT_LIST_ENTRY.format(col.displayName))
    }

    success(message)
    return seats.size
}

private fun GameCommandContext.seatsClear(): Int {
    val town = source.server.town ?: commandError(CommandMessages.Town.HAS_NO_TOWN)

    town.clearSeats()

    success(CommandMessages.Town.CLEARED_SEATS)
    return 1
}

private fun handleSeatError(error: Int) {
    when (error) {
        -1 -> commandError(CommandMessages.Town.SEAT_COLOR_IN_USE)
        -2 -> commandError(CommandMessages.Town.SEAT_COLOR_NOT_IN_USE)
        -3 -> commandError(CommandMessages.Town.MAX_SEATS_REACHED)
    }
}

private fun GameCommandContext.seatsAdd(color: SeatColor? = null): Int {
    val town = source.server.town ?: commandError(CommandMessages.Town.HAS_NO_TOWN)

    val result = if (color == null) {
        town.addSeat(TownSeat())
    } else {
        town.addSeat(color, TownSeat())
    }
    handleSeatError(result)

    success(CommandMessages.Town.ADDED_SEAT, SeatColor.entries[result].displayName)
    return result
}

private fun GameCommandContext.seatsRemove(color: SeatColor): Int {
    val town = source.server.town ?: commandError(CommandMessages.Town.HAS_NO_TOWN)

    val result = town.removeSeat(color)
    handleSeatError(result)

    success(CommandMessages.Town.REMOVED_SEAT, SeatColor.entries[result].displayName)
    return result
}

private fun GameCommandContext.seatsAlloc(size: Int, type: Int): Int {
    val town = source.server.town ?: commandError(CommandMessages.Town.HAS_NO_TOWN)

    val beforeSize = town.seats().size

    val result = when (type) {
        1 -> town.allocateAtMost(size)
        2 -> town.allocateAtLeast(size)
        else -> town.allocateExactly(size)
    }
    handleSeatError(result)

    val afterSize = town.seats().size

    when {
        afterSize < beforeSize -> success(CommandMessages.Town.REMOVED_SEATS, beforeSize - afterSize)
        afterSize > beforeSize -> success(CommandMessages.Town.ADDED_SEATS, afterSize - beforeSize)
        else -> success(CommandMessages.Town.NO_SEATS_CHANGED)
    }

    return result
}

