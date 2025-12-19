package dev.runefox.blocktower.client.util

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.player.LocalPlayer
import net.minecraft.core.RegistryAccess

val Minecraft get() = net.minecraft.client.Minecraft.getInstance()

val CurrentLevel: ClientLevel? get() = Minecraft.level
val CurrentPlayer: LocalPlayer? get() = Minecraft.player
val CurrentRegistry: RegistryAccess? get() = CurrentLevel?.registryAccess()
