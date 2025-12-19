package dev.runefox.blocktower.client.gui

import com.mojang.blaze3d.platform.cursor.CursorTypes
import dev.runefox.blocktower.client.util.Minecraft
import dev.runefox.blocktower.client.util.button
import dev.runefox.blocktower.client.util.display
import dev.runefox.blocktower.common.model.Role
import dev.runefox.blocktower.common.model.RoleType
import dev.runefox.blocktower.common.model.Script
import dev.runefox.blocktower.common.model.buildFullDescription
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.TextAlignment
import net.minecraft.client.gui.components.AbstractSelectionList
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.input.MouseButtonEvent
import net.minecraft.network.chat.CommonComponents.GUI_DONE
import net.minecraft.network.chat.Component

/**
 * Screen that displays a script. Opened when a script item is clicked.
 */
@Environment(EnvType.CLIENT)
class ScriptScreen(val script: Script) : Screen(GuiTexts.SCRIPT_OVERVIEW) {
    private lateinit var roles: RoleList

    override fun init() {
        super.init()

        if (!::roles.isInitialized) {
            roles = RoleList(minecraft, width, height - (48 + 32), 48)
        } else {
            roles.updateSizeAndPosition(width, height - (48 + 32), 48)
        }

        addRenderableWidget(roles)

        addRenderableWidget(button(GUI_DONE, ::onClose) {
            pos(width / 2 - 75, height - 26)
        })
    }

    override fun render(g: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(g, mouseX, mouseY, partialTick)

        val pose = g.pose()

        pose.pushMatrix()
        pose.translate((width / 2).toFloat(), 24f)
        pose.scale(2f)

        g.textRenderer().accept(TextAlignment.CENTER, 0, -4, script.name)

        pose.popMatrix()
    }

    override fun isInGameUi(): Boolean {
        return true
    }

    override fun isPauseScreen(): Boolean {
        return false
    }

    inner class RoleList(
        mc: Minecraft,
        width: Int, height: Int,
        y: Int
    ) : AbstractSelectionList<RoleList.BaseEntry>(mc, width, height, y, 32) {
        init {
            val entry = DescriptionEntry(script.buildFullDescription(), rowWidth - 8)
            addEntry(entry, entry.suggestedHeight)

            addEntry(CategoryEntry(RoleType.TOWNSFOLK))
            for ((_, role) in script.roles.townsfolk) {
                addEntry(RoleEntry(role.value(), 256))
            }

            addEntry(CategoryEntry(RoleType.OUTSIDER))
            for ((_, role) in script.roles.outsiders) {
                addEntry(RoleEntry(role.value(), 256))
            }

            addEntry(CategoryEntry(RoleType.MINION))
            for ((_, role) in script.roles.minions) {
                addEntry(RoleEntry(role.value(), 256))
            }

            addEntry(CategoryEntry(RoleType.DEMON))
            for ((_, role) in script.roles.demons) {
                addEntry(RoleEntry(role.value(), 256))
            }
        }

        override fun getRowWidth(): Int {
            return 128*3
        }

        override fun updateWidgetNarration(output: NarrationElementOutput) {
        }

        override fun entriesCanBeSelected(): Boolean {
            return false
        }

        abstract inner class BaseEntry : Entry<BaseEntry>()

        inner class RoleEntry(val role: Role, val innerWidth: Int) : BaseEntry() {
            override fun mouseClicked(event: MouseButtonEvent, double: Boolean): Boolean {
                playButtonClickSound(Minecraft.soundManager)

                RoleScreen(role, this@ScriptScreen).display()
                return true
            }

            override fun renderContent(g: GuiGraphics, mouseX: Int, mouseY: Int, hovered: Boolean, partialTick: Float) {
                g.drawRoleBar(role, x + width / 2 - innerWidth / 2, y + height / 2, innerWidth, highlight = hovered)

                if (hovered) {
                    g.requestCursor(CursorTypes.POINTING_HAND)
                }
            }
        }

        inner class CategoryEntry(val category: RoleType) : BaseEntry() {
            override fun renderContent(g: GuiGraphics, mouseX: Int, mouseY: Int, hovered: Boolean, partialTick: Float) {
                val title = category.title.copy().apply {
                    style = style.withColor(category.alignment.style.textStyle)
                }
                g.textRenderer().accept(TextAlignment.CENTER, width / 2 + x, height - font.lineHeight - 4 + y, title)

                g.fill(x, y + height - 1, x + width, y + height, 0x88FFFFFFU.toInt())
            }
        }

        inner class DescriptionEntry(text: Component, textWidth: Int) : BaseEntry() {
            val lines = font.split(text, textWidth)
            val suggestedHeight get() = lines.size * font.lineHeight + 16

            override fun renderContent(g: GuiGraphics, mouseX: Int, mouseY: Int, hovered: Boolean, partialTick: Float) {
                var y = y + 4
                val text = g.textRenderer()
                for (line in lines) {
                    text.accept(TextAlignment.CENTER, width / 2 + x, y, line)
                    y += font.lineHeight
                }
            }
        }
    }
}
