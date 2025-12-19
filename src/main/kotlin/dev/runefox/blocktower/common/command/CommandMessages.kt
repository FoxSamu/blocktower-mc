package dev.runefox.blocktower.common.command

import dev.runefox.blocktower.common.util.*
import dev.runefox.blocktower.data.Datagen
import net.minecraft.ChatFormatting
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.ClickEvent
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentUtils
import net.minecraft.network.chat.HoverEvent

object CommandMessages {
    object Town {
        val ALREADY_HAS_TOWN = message("town.already_has_town", "This world already has a Blocktower town")
        val HAS_NO_TOWN = message("town.has_no_town", "This world does not have a Blocktower town")
        val INITIALIZED = message("town.initialized", "A new town was initialized for this world")
        val DELETED = message("town.deleted", "The town of this world has been deleted")
        val GET_CENTER = message("town.get_center", "The town center is at %s")
        val SET_CENTER = message("town.set_center", "The town center has been set to %s")
    }

    init {
        // Make sure we initialize inner classes along with this object
        Town
    }

    fun message(key: String, translation: String): Formattable {
        val key = "commands.blocktower.$key"
        Datagen.TRANSLATIONS[key] = translation
        return key.translate()
    }

    fun formatBlockPos(pos: BlockPos): Component {
        return ComponentUtils.wrapInSquareBrackets(Component.translatable("chat.coordinates", pos.x, pos.y, pos.z)) + MapStyle {
            it
                .withColor(ChatFormatting.GREEN)
                .withClickEvent(ClickEvent.SuggestCommand("/tp @s ${pos.x} ${pos.y} ${pos.z}"))
                .withHoverEvent(HoverEvent.ShowText(Component.translatable("chat.coordinates.tooltip")))
        }
    }
}
