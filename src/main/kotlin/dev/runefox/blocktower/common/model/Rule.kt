package dev.runefox.blocktower.common.model

import net.minecraft.network.chat.Component
import java.util.*

data class Rule(
    val description: Component,
    val condition: Component? = null,
) {
    constructor(description: Component, condition: Optional<Component>) : this(description, condition.orElse(null))
}
