package dev.runefox.blocktower.common.command

import dev.runefox.blocktower.common.util.*
import net.minecraft.commands.arguments.coordinates.BlockPosArgument.*
import net.minecraft.commands.arguments.coordinates.Coordinates
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

    success(CommandMessages.Town.GET_CENTER, CommandMessages.formatBlockPos(town.center))
    return 1
}

private fun GameCommandContext.center(pos: Coordinates): Int {
    val pos = pos.getBlockPos(source)

    val town = source.server.town ?: commandError(CommandMessages.Town.HAS_NO_TOWN)
    town.center = pos

    success(CommandMessages.Town.SET_CENTER, CommandMessages.formatBlockPos(pos))
    return 1
}
