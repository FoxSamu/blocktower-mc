package dev.runefox.blocktower.common.model

import dev.runefox.blocktower.common.util.fieldOf
import dev.runefox.blocktower.common.util.forGetter
import dev.runefox.blocktower.common.util.forNullGetter
import dev.runefox.blocktower.common.util.optional
import dev.runefox.blocktower.common.util.recordCodec
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentSerialization
import java.util.Optional

data class Rule(
    val description: Component,
    val condition: Component? = null,
) {
    constructor(description: Component, condition: Optional<Component>) : this(description, condition.orElse(null))

    companion object {
        val CODEC = recordCodec<Rule> {
            group(
                ComponentSerialization.CODEC fieldOf "description" forGetter { description },
                ComponentSerialization.CODEC fieldOf optional("condition") forNullGetter { condition }
            ).apply(this, ::Rule)
        }
    }
}
