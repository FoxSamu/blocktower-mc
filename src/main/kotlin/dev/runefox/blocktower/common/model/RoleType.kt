package dev.runefox.blocktower.common.model

import dev.runefox.blocktower.common.util.SerialEnum
import net.minecraft.network.chat.Component

enum class RoleType(override val serialName: String, val alignment: Alignment, val title: Component) : SerialEnum {
    TOWNSFOLK("townsfolk", Alignment.GOOD, Component.literal("Townsfolk")),
    OUTSIDER("outsider", Alignment.GOOD, Component.literal("Outsiders")),
    MINION("minion", Alignment.EVIL, Component.literal("Minions")),
    DEMON("demon", Alignment.EVIL, Component.literal("Demons")),
    TRAVELLER("traveller", Alignment.GOOD, Component.literal("Travellers"));
}
