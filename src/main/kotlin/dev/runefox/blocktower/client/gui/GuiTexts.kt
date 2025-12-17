package dev.runefox.blocktower.client.gui

import dev.runefox.blocktower.data.Datagen
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.network.chat.Component

@Environment(EnvType.CLIENT)
object GuiTexts {
    val SCRIPT_OVERVIEW = create("gui.blocktower.script_overview", "Script Overview")

    fun create(key: String, translation: String): Component {
        Datagen.TRANSLATIONS[key] = translation
        return Component.translatable(key)
    }
}
