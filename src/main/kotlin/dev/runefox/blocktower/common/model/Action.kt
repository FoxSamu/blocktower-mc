package dev.runefox.blocktower.common.model

import net.minecraft.network.chat.Component
import java.util.*

data class Action(
    val role: String,

    val description: Component,
    val condition: Component? = null,
) {
    constructor(role: String, description: Component, condition: Optional<Component>) : this(role, description, condition.orElse(null))
}
