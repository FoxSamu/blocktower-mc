package dev.runefox.blocktower.client.gui

import dev.runefox.blocktower.common.model.Alignment
import dev.runefox.blocktower.common.model.Role
import dev.runefox.blocktower.common.util.blocktower
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.RenderPipelines

@Environment(EnvType.CLIENT)
private object RoleSprites {
    const val SIZE = 28
    const val RADIUS = SIZE / 2

    val GOOD = blocktower("frame/good")
    val EVIL = blocktower("frame/evil")
    val NEUTRAL = blocktower("frame/neutral")
    val FABLED = blocktower("frame/fabled")
    val LORIC = blocktower("frame/loric")

    val GOOD_HIGHLIGHTED = blocktower("frame/good_highlighted")
    val EVIL_HIGHLIGHTED = blocktower("frame/evil_highlighted")
    val NEUTRAL_HIGHLIGHTED = blocktower("frame/neutral_highlighted")
    val FABLED_HIGHLIGHTED = blocktower("frame/fabled_highlighted")
    val LORIC_HIGHLIGHTED = blocktower("frame/loric_highlighted")

    fun good(highlighted: Boolean) = if (highlighted) GOOD_HIGHLIGHTED else GOOD
    fun evil(highlighted: Boolean) = if (highlighted) EVIL_HIGHLIGHTED else EVIL
    fun neutral(highlighted: Boolean) = if (highlighted) NEUTRAL_HIGHLIGHTED else NEUTRAL
    fun fabled(highlighted: Boolean) = if (highlighted) FABLED_HIGHLIGHTED else FABLED
    fun loric(highlighted: Boolean) = if (highlighted) LORIC_HIGHLIGHTED else LORIC

    fun alignment(alignment: Alignment, highlighted: Boolean) = when (alignment) {
        Alignment.GOOD -> good(highlighted)
        Alignment.EVIL -> evil(highlighted)
    }
}

@Environment(EnvType.CLIENT)
fun GuiGraphics.drawRole(role: Role, x: Int, y: Int, alignment: Alignment = role.alignment, highlight: Boolean = false) {
    val r = RoleSprites.RADIUS
    val s = RoleSprites.SIZE

    blitSprite(RenderPipelines.GUI_TEXTURED, RoleSprites.alignment(alignment, highlight), x - r, y - r, s, s)
    renderItem(role.icon, x - 8, y - 8)
}
