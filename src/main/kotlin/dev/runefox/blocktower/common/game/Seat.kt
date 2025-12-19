package dev.runefox.blocktower.common.game

import dev.runefox.blocktower.common.model.Role
import net.minecraft.core.Holder
import java.util.UUID

class Seat {
    var playerUuid: UUID? = null
    var role: Holder<Role>? = null
}
