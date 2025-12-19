package dev.runefox.blocktower.common.util

import dev.runefox.blocktower.common.map.Town
import dev.runefox.blocktower.inject.MinecraftServerInj
import net.minecraft.server.MinecraftServer

val MinecraftServer.town: Town?
    get() {
        this as MinecraftServerInj
        return `blocktower$getTown`()
    }

fun MinecraftServer.initTown(): Town {
    this as MinecraftServerInj
    return `blocktower$initTown`()
}

fun MinecraftServer.deleteTown(): Boolean {
    this as MinecraftServerInj
    return `blocktower$deleteTown`()
}
