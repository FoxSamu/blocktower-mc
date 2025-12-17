package dev.runefox.blocktower.client.util

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen

@Environment(EnvType.CLIENT)
fun Screen.display() {
    Minecraft.getInstance().setScreen(this)
}
