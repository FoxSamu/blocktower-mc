package dev.runefox.blocktower.common.model

import com.mojang.serialization.Codec
import dev.runefox.blocktower.common.ModRegistries
import dev.runefox.blocktower.common.util.fieldOf
import dev.runefox.blocktower.common.util.forGetter
import dev.runefox.blocktower.common.util.forNullGetter
import dev.runefox.blocktower.common.util.holder
import dev.runefox.blocktower.common.util.holderSet
import dev.runefox.blocktower.common.util.optional
import dev.runefox.blocktower.common.util.recordCodec
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentSerialization
import net.minecraft.world.item.ItemStack
import java.util.Optional

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

    constructor(
        type: RoleType,
        name: Component,
        description: Component,
        icon: ItemStack,
        lore: Optional<Component>,
        alignmentOverride: Optional<Alignment>,
        wikiUrl: Optional<String>,
        rules: List<Rule>,
        tokens: List<Token>,
    ) : this(type, name, description, icon, lore.orElse(null), alignmentOverride.orElse(null), wikiUrl.orElse(null), rules, tokens)

    companion object {
        val DIRECT_CODEC = recordCodec<Role> {
            group(
                RoleType.CODEC fieldOf "type" forGetter Role::type,
                ComponentSerialization.CODEC fieldOf "name" forGetter Role::name,
                ComponentSerialization.CODEC fieldOf "description" forGetter Role::description,
                ItemStack.CODEC fieldOf "icon" forGetter Role::icon,
                ComponentSerialization.CODEC fieldOf optional("lore") forNullGetter Role::lore,
                Alignment.CODEC fieldOf optional("alignment_override") forNullGetter Role::alignmentOverride,
                Codec.STRING fieldOf optional("wiki_url") forNullGetter Role::wikiUrl,
                Rule.CODEC.listOf() fieldOf optional("rules", emptyList()) forGetter Role::rules,
                Token.CODEC.listOf() fieldOf optional("tokens", emptyList()) forGetter Role::tokens,
            ).apply(this, ::Role)
        }

        val CODEC = DIRECT_CODEC.holder(ModRegistries.ROLE)
        val LIST_CODEC = DIRECT_CODEC.holderSet(ModRegistries.ROLE)
    }
}
