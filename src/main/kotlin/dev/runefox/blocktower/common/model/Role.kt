package dev.runefox.blocktower.common.model

import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import java.util.*

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
    override val name: Component,

    /**
     * Description of the role. Explains to the player what the role does.
     */
    override val description: Component,

    /**
     * The item to display as role icon.
     */
    override val icon: ItemStack,

    /**
     * Short lore text.
     */
    override val lore: Component? = null,

    /**
     * If not null, overrides the alignment of [type].
     */
    val alignmentOverride: Alignment? = null,

    /**
     * An URL pointing to a wiki page about this role.
     */
    override val wikiUrl: String? = null,

    /**
     * A list of tokens that this role makes available to add to people in the grimoire.
     */
    val tokens: List<Token> = emptyList(),

    /**
     * A rule that applies the first night.
     */
    val nightRule: NightMap<Rule?> = NightMap(null, null),
) : Character {
    constructor(
        type: RoleType,
        name: Component,
        description: Component,
        icon: ItemStack,
        lore: Optional<Component>,
        alignmentOverride: Optional<Alignment>,
        wikiUrl: Optional<String>,
        tokens: List<Token>,
        nightRule: NightMap<Rule?>
    ) : this(
        type,
        name,
        description,
        icon,
        lore.orElse(null),
        alignmentOverride.orElse(null),
        wikiUrl.orElse(null),
        tokens,
        nightRule
    )

    val alignment get() = alignmentOverride ?: type.alignment

    override val style get() = alignment.style
}
