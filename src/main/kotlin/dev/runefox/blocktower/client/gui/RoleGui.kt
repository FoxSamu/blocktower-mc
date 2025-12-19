package dev.runefox.blocktower.client.gui

import dev.runefox.blocktower.common.model.Character
import dev.runefox.blocktower.common.model.CharacterStyle
import dev.runefox.blocktower.common.model.Role
import dev.runefox.blocktower.common.util.blocktower
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractButton
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.client.input.InputWithModifiers
import net.minecraft.client.renderer.RenderPipelines

@Environment(EnvType.CLIENT)
private object RoleSprites {
    const val SIZE = 28
    const val RADIUS = SIZE / 2
    const val HEADER_HEIGHT = 24

    val HEADER = blocktower("header_box")

    val GOOD = blocktower("frame/good")
    val EVIL = blocktower("frame/evil")
    val FABLED = blocktower("frame/fabled")
    val LORIC = blocktower("frame/loric")

    val GOOD_HIGHLIGHTED = blocktower("frame/good_highlighted")
    val EVIL_HIGHLIGHTED = blocktower("frame/evil_highlighted")
    val FABLED_HIGHLIGHTED = blocktower("frame/fabled_highlighted")
    val LORIC_HIGHLIGHTED = blocktower("frame/loric_highlighted")

    fun good(highlighted: Boolean) = if (highlighted) GOOD_HIGHLIGHTED else GOOD
    fun evil(highlighted: Boolean) = if (highlighted) EVIL_HIGHLIGHTED else EVIL
    fun fabled(highlighted: Boolean) = if (highlighted) FABLED_HIGHLIGHTED else FABLED
    fun loric(highlighted: Boolean) = if (highlighted) LORIC_HIGHLIGHTED else LORIC

    fun style(style: CharacterStyle, highlighted: Boolean) = when (style) {
        CharacterStyle.GOOD -> good(highlighted)
        CharacterStyle.EVIL -> evil(highlighted)
        CharacterStyle.FABLED -> fabled(highlighted)
        CharacterStyle.LORIC -> loric(highlighted)
    }
}

fun GuiGraphics.drawRole(role: Character, x: Int, y: Int, highlight: Boolean = false) {
    val r = RoleSprites.RADIUS
    val s = RoleSprites.SIZE

    blitSprite(RenderPipelines.GUI_TEXTURED, RoleSprites.style(role.style, highlight), x - r, y - r, s, s)
    renderItem(role.icon, x - 8, y - 8)
}

fun GuiGraphics.drawRoleBar(role: Character, x: Int, y: Int, width: Int, highlight: Boolean = false) {
    val r = RoleSprites.RADIUS
    val s = RoleSprites.SIZE
    val h = RoleSprites.HEADER_HEIGHT

    drawHeaderBox(x + r, y - h / 2, width - r, h)
    drawRole(role, x + r, y, highlight)

    val name = role.name.copy().apply {
        style = style.withColor(role.style.textStyle)
    }

    textRenderer().accept(x + s + 2, y - 4, name)
}

fun GuiGraphics.drawHeaderBox(x: Int, y: Int, w: Int, h: Int) {
    blitSprite(RenderPipelines.GUI_TEXTURED, RoleSprites.HEADER, x, y, w, h)
}

class RoleBarWidget(
    private val role: Role,
    x: Int, y: Int,
    w: Int,

    private val click: ((Role) -> Unit)? = null,
) : AbstractButton(x, y, w, 28, role.name) {
    override fun onPress(input: InputWithModifiers) {
        click?.invoke(role)
    }

    override fun renderContents(g: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        val highlight = click != null && isMouseOver(mouseX.toDouble(), mouseY.toDouble())

        val iconX = x + 14
        val iconY = y + 14

        val backdropX = x + 2
        val backdropY = y + 2
        val backdropW = width - 4
        val backdropH = height - 4

        g.drawHeaderBox(backdropX, backdropY, backdropW, backdropH)
        g.drawRole(role, iconX, iconY, highlight)
        g.textRenderer().accept(iconX + 16, iconY - 4, role.name)

        if (isMouseOver(mouseX.toDouble(), mouseY.toDouble())) {
            val font = Minecraft.getInstance().font
            val lines = font.split(role.description, width - 16)
            g.setTooltipForNextFrame(lines, mouseX, mouseY)
        }
    }

    override fun updateWidgetNarration(narrator: NarrationElementOutput) {
        defaultButtonNarrationText(narrator)
    }
}
