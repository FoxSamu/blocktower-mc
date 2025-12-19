package dev.runefox.blocktower.client.gui

import dev.runefox.blocktower.common.util.blocktower
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.RenderPipelines.GUI_TEXTURED

private object DecorSprites {
    val LEFT = blocktower("title_decor_left")
    val RIGHT = blocktower("title_decor_right")
}

fun GuiGraphics.drawDecorationAround(x: Int, y: Int, w: Int) {
    blitSprite(GUI_TEXTURED, DecorSprites.LEFT, x - 128, y - 8, 128, 16)
    blitSprite(GUI_TEXTURED, DecorSprites.RIGHT, x + w, y - 8, 128, 16)
}
