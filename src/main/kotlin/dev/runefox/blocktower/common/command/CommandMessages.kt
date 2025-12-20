package dev.runefox.blocktower.common.command

import dev.runefox.blocktower.common.map.Location
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

        val SEAT_LIST = message("town.seat_list", "There are %s seats:")
        val SEAT_LIST_ENTRY = message("town.seat_list_entry", "  - %s")

        val CLEARED_SEATS = message("town.cleared_Seats", "Removed all seats")
        val ADDED_SEATS = message("town.added_seats", "Added %s seats")
        val REMOVED_SEATS = message("town.removed_seats", "Removed %s seats")
        val ADDED_SEAT = message("town.added_seat", "Added seat %s")
        val REMOVED_SEAT = message("town.removed_seat", "Removed seat %s")
        val NO_SEATS_CHANGED = message("town.no_seats_changed", "No seats were added or removed")

        val SEAT_COLOR_IN_USE = message("town.seat_color_in_use", "Seat color already in use")
        val SEAT_COLOR_NOT_IN_USE = message("town.seat_color_not_in_use", "Seat color is not in use")
        val MAX_SEATS_REACHED = message("town.max_seats_reached", "Maximum number of seats reached")
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

    fun formatLocation(loc: Location): Component {
        val pos = loc.pos

        return ComponentUtils.wrapInSquareBrackets(Component.translatable("chat.coordinates", pos.x, pos.y, pos.z)) + MapStyle {
            it
                .withColor(ChatFormatting.GREEN)
                .withClickEvent(ClickEvent.SuggestCommand("/execute in ${loc.dimension.identifier()} run tp @s ${pos.x} ${pos.y} ${pos.z}"))
                .withHoverEvent(HoverEvent.ShowText(Component.translatable("chat.coordinates.tooltip")))
        }
    }
}
