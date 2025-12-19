package dev.runefox.blocktower.common.model

import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import java.util.*

/**
 * A storyteller character that helps the storyteller balance the game more. Can be introduced and removed at any time during the game.
 */
data class Fabled(
    /**
     * Name of the character.
     */
    override val name: Component,

    /**
     * Description of the character. Explains to the player what the character does.
     */
    override val description: Component,

    /**
     * The item to display as character icon.
     */
    override val icon: ItemStack,

    /**
     * Short lore text.
     */
    override val lore: Component? = null,

    /**
     * An URL pointing to a wiki page about this character.
     */
    override val wikiUrl: String? = null,

    /**
     * A list of tokens that this character makes available to add to people in the grimoire.
     */
    val tokens: List<Token> = emptyList(),

    /**
     * A list of rules that apply when this character is in play.
     */
    override val rules: List<Rule> = emptyList(),
) : StorytellerCharacter {
    constructor(
        name: Component,
        description: Component,
        icon: ItemStack,
        lore: Optional<Component>,
        wikiUrl: Optional<String>,
        tokens: List<Token>,
        rules: List<Rule>
    ) : this(
        name,
        description,
        icon,
        lore.orElse(null),
        wikiUrl.orElse(null),
        tokens,
        rules
    )

    override val style get() = CharacterStyle.FABLED
}
