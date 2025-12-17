@file:Suppress("unused")

package dev.runefox.blocktower.common.model

import com.mojang.serialization.Codec
import dev.runefox.blocktower.common.ModRegistries
import dev.runefox.blocktower.common.util.fieldOf
import dev.runefox.blocktower.common.util.forGetter
import dev.runefox.blocktower.common.util.forNullGetter
import dev.runefox.blocktower.common.util.holder
import dev.runefox.blocktower.common.util.holderSet
import dev.runefox.blocktower.common.util.list
import dev.runefox.blocktower.common.util.mapping
import dev.runefox.blocktower.common.util.optional
import dev.runefox.blocktower.common.util.recordCodec
import net.minecraft.core.Holder
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentSerialization
import java.util.Optional

data class Script(
    val name: Component,
    val description: Component,

    val roles: Map<String, Holder<Role>>,

    val setupOrder: List<Rule>,
    val firstNightOrder: List<Action>,
    val nightOrder: List<Action>,

    val minPlayers: Int,
    val distribution: List<Distribution>,

    val lore: Component? = null,

    val tokens: List<Token> = emptyList(),
) {
    constructor(
        name: Component,
        description: Component,
        roles: Map<String, Holder<Role>>,
        setupOrder: List<Rule>,
        firstNightOrder: List<Action>,
        nightOrder: List<Action>,
        minPlayers: Int,
        distribution: List<Distribution>,
        lore: Optional<Component>,
        tokens: List<Token>
    ) : this(name, description, roles, setupOrder, firstNightOrder, nightOrder, minPlayers, distribution, lore.orElse(null), tokens)

    companion object {
        val DIRECT_CODEC = recordCodec<Script> {
            group(
                ComponentSerialization.CODEC fieldOf "name" forGetter { name },
                ComponentSerialization.CODEC fieldOf "description" forGetter { description },
                Codec.STRING mapping Role.CODEC fieldOf "roles" forGetter { roles },
                Rule.CODEC.list fieldOf "setup_order" forGetter { setupOrder },
                Action.CODEC.list fieldOf "first_night_order" forGetter { firstNightOrder },
                Action.CODEC.list fieldOf "night_order" forGetter { nightOrder },
                Codec.intRange(1, 256) fieldOf "min_players" forGetter { minPlayers },
                Distribution.CODEC.list fieldOf "distribution" forGetter { distribution },
                ComponentSerialization.CODEC fieldOf optional("lore") forNullGetter { lore },
                Token.CODEC.list fieldOf optional("lore", emptyList()) forGetter { tokens },
            ).apply(this, ::Script)
        }

        val CODEC = DIRECT_CODEC.holder(ModRegistries.SCRIPT, allowInline = false)
        val LIST_CODEC = DIRECT_CODEC.holderSet(ModRegistries.SCRIPT, allowInline = false)
    }
}
