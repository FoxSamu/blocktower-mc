package dev.runefox.blocktower.client.gui

import dev.runefox.blocktower.client.util.button
import dev.runefox.blocktower.client.util.display
import dev.runefox.blocktower.common.model.Character
import dev.runefox.blocktower.common.model.buildFullDescription
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.TextAlignment
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents.GUI_DONE

class RoleScreen(val role: Character, val parent: Screen) : Screen(role.name) {
    override fun init() {
        addRenderableWidget(button(GUI_DONE, ::onClose) {
            pos(width / 2 - 75, height - 26)
        })
    }

    override fun render(g: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(g, mouseX, mouseY, partialTick)

        val pose = g.pose()

        pose.pushMatrix()
        pose.translate((width / 2).toFloat(), 48f)
        pose.scale(2f)

        g.drawRole(role, 0, 0)

        g.textRenderer().accept(TextAlignment.CENTER, 0, 18, role.name)

        pose.popMatrix()

        val text = g.textRenderer()

        val fullDesc = role.buildFullDescription()

        val lines = font.split(fullDesc, width - 200)
        var y = 48 + 2*18 + 3*font.lineHeight

        for (line in lines) {
            text.accept(TextAlignment.CENTER, width / 2, y, line)
            y += font.lineHeight
        }
    }

    override fun onClose() {
        parent.display()
    }

    override fun isInGameUi(): Boolean {
        return true
    }

    override fun isPauseScreen(): Boolean {
        return false
    }
}
