package dev.runefox.blocktower.client.gui

import dev.runefox.blocktower.common.ModRoles
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen

@Environment(EnvType.CLIENT)
class ScriptScreen : Screen(GuiTexts.SCRIPT_OVERVIEW) {
    override fun render(g: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(g, mouseX, mouseY, partialTick)

        val role = minecraft.level?.registryAccess()[ModRoles.EMPATH]?.orElse(null)?.value()!!

        g.drawRole(role, width / 2, height / 2)
    }

    override fun isInGameUi(): Boolean {
        return true
    }

    override fun isPauseScreen(): Boolean {
        return false
    }
}
