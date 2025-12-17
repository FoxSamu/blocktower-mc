package dev.runefox.blocktower.common.model

import com.mojang.serialization.Codec
import dev.runefox.blocktower.common.util.fieldOf
import dev.runefox.blocktower.common.util.forGetter
import dev.runefox.blocktower.common.util.forNullGetter
import dev.runefox.blocktower.common.util.optional
import dev.runefox.blocktower.common.util.recordCodec
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentSerialization
import java.util.Optional

data class Action(
    val role: String,

    val description: Component,
    val condition: Component? = null,
) {
    constructor(role: String, description: Component, condition: Optional<Component>) : this(role, description, condition.orElse(null))

    companion object {
        val CODEC = recordCodec<Action> {
            group(
                Codec.STRING fieldOf "role" forGetter { role },
                ComponentSerialization.CODEC fieldOf "description" forGetter { description },
                ComponentSerialization.CODEC fieldOf optional("condition") forNullGetter { condition }
            ).apply(this, ::Action)
        }
    }
}
