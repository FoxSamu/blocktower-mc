@file:Suppress("unused")

package dev.runefox.blocktower

import com.mojang.serialization.Codec
import dev.runefox.blocktower.util.*
import net.minecraft.core.Holder
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentSerialization
import net.minecraft.world.item.ItemStack

enum class Alignment(override val serialName: String) : SerialEnum {
    GOOD("good"),
    EVIL("evil"),
    FABLED("fabled");

    companion object {
        val CODEC = enumCodec<Alignment>()
    }
}

enum class RoleType(override val serialName: String, val alignment: Alignment) : SerialEnum {
    TOWNSFOLK("townsfolk", Alignment.GOOD),
    OUTSIDER("outsider", Alignment.GOOD),
    MINION("minion", Alignment.EVIL),
    DEMON("demon", Alignment.EVIL),
    TRAVELLER("traveller", Alignment.GOOD),
    FABLED("fabled", Alignment.FABLED);

    companion object {
        val CODEC = enumCodec<RoleType>()
    }
}

/**
 * A role that a player can play as.
 */
data class Role(
    /**
     * Type of the role.
     */
    val type: RoleType,

    /**
     * Name of the role.
     */
    val name: Component,

    /**
     * Description of the role. Explains to the player what the role does.
     */
    val description: Component,

    /**
     * The item to display as role icon.
     */
    val icon: ItemStack,

    /**
     * Short lore text.
     */
    val lore: Component? = null,

    /**
     * If not null, overrides the alignment of [type].
     */
    val alignmentOverride: Alignment? = null,

    /**
     * An URL pointing to a wiki page about this role.
     */
    val wikiUrl: String? = null,

    /**
     * A list of rules that the storyteller can see in their grimoire.
     */
    val rules: List<Rule> = emptyList(),

    /**
     * A list of tokens that this role makes available to add to people in the grimoire.
     */
    val tokens: List<Token> = emptyList(),
) {
    val alignment get() = alignmentOverride ?: type.alignment

    companion object {
        val DIRECT_CODEC = recordCodec<Role> {
            group(
                RoleType.CODEC fieldOf "type" forGetter Role::type,
                ComponentSerialization.CODEC fieldOf "name" forGetter Role::name,
                ComponentSerialization.CODEC fieldOf "description" forGetter Role::description,
                ItemStack.CODEC fieldOf "icon" forGetter Role::icon,
                ComponentSerialization.CODEC fieldOf optional("lore") forGetter Role::lore,
                Alignment.CODEC fieldOf optional("alignment_override") forGetter Role::alignmentOverride,
                Codec.STRING fieldOf optional("wiki_url") forGetter Role::wikiUrl,
                Rule.CODEC.listOf() fieldOf optional("rules", emptyList()) forGetter Role::rules,
                Token.CODEC.listOf() fieldOf optional("tokens", emptyList()) forGetter Role::tokens,
            ).apply(this, ::Role)
        }

        val CODEC = DIRECT_CODEC.holder(ModRegistries.ROLE)
        val LIST_CODEC = DIRECT_CODEC.holderSet(ModRegistries.ROLE)
    }
}

/**
 * A reminder token.
 */
data class Token(
    val icon: ItemStack,
    val name: Component,
) {
    companion object {
        val CODEC = recordCodec<Token> {
            group(
                ItemStack.CODEC fieldOf "icon" forGetter { icon },
                ComponentSerialization.CODEC fieldOf "name" forGetter { name }
            ).apply(this, ::Token)
        }
    }
}

data class Rule(
    val description: Component,
    val condition: Component? = null,
) {
    companion object {
        val CODEC = recordCodec<Rule> {
            group(
                ComponentSerialization.CODEC fieldOf "description" forGetter { description },
                ComponentSerialization.CODEC fieldOf optional("condition") forGetter { condition }
            ).apply(this, ::Rule)
        }
    }
}

data class Action(
    val role: String,

    val description: Component,
    val condition: Component? = null,
) {
    companion object {
        val CODEC = recordCodec<Action> {
            group(
                Codec.STRING fieldOf "role" forGetter { role },
                ComponentSerialization.CODEC fieldOf "description" forGetter { description },
                ComponentSerialization.CODEC fieldOf optional("condition") forGetter { condition }
            ).apply(this, ::Action)
        }
    }
}

data class Distribution(
    val townsfolk: Int,
    val outsiders: Int,
    val minions: Int,
    val demons: Int
) {
    companion object {
        val CODEC = recordCodec<Distribution> {
            group(
                Codec.intRange(0, 256) fieldOf "townsfolk" forGetter { townsfolk },
                Codec.intRange(0, 256) fieldOf "outsiders" forGetter { outsiders },
                Codec.intRange(0, 256) fieldOf "minions" forGetter { minions },
                Codec.intRange(0, 256) fieldOf "demons" forGetter { demons },
            ).apply(this, ::Distribution)
        }
    }
}

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
    companion object {
        val DIRECT_CODEC = recordCodec<Script> {
            group(
                ComponentSerialization.CODEC fieldOf "name" forGetter { name },
                ComponentSerialization.CODEC fieldOf "description" forGetter { description },
                Codec.unboundedMap(Codec.STRING, Role.CODEC) fieldOf "roles" forGetter { roles },
                Rule.CODEC.list fieldOf "setup_order" forGetter { setupOrder },
                Action.CODEC.list fieldOf "first_night_order" forGetter { firstNightOrder },
                Action.CODEC.list fieldOf "night_order" forGetter { nightOrder },
                Codec.intRange(1, 256) fieldOf "min_players" forGetter { minPlayers },
                Distribution.CODEC.list fieldOf "distribution" forGetter { distribution },
                ComponentSerialization.CODEC fieldOf optional("lore") forGetter { lore },
                Token.CODEC.list fieldOf optional("lore", emptyList()) forGetter { tokens },
            ).apply(this, ::Script)
        }

        val CODEC = DIRECT_CODEC.holder(ModRegistries.SCRIPT, allowInline = false)
        val LIST_CODEC = DIRECT_CODEC.holderSet(ModRegistries.SCRIPT, allowInline = false)
    }
}
