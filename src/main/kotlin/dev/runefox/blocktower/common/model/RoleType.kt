package dev.runefox.blocktower.common.model

import dev.runefox.blocktower.common.util.SerialEnum
import dev.runefox.blocktower.common.util.enumCodec

enum class RoleType(override val serialName: String, val alignment: Alignment) : SerialEnum {
    TOWNSFOLK("townsfolk", Alignment.GOOD),
    OUTSIDER("outsider", Alignment.GOOD),
    MINION("minion", Alignment.EVIL),
    DEMON("demon", Alignment.EVIL),
    TRAVELLER("traveller", Alignment.GOOD);

    companion object {
        val CODEC = enumCodec<RoleType>()
    }
}
