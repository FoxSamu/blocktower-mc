package dev.runefox.blocktower.common.model

import dev.runefox.blocktower.common.util.T
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

class StoryCharacterBuilder {
    var name: Component = T()
    var description: Component = T()
    var icon: ItemStack = ItemStack.EMPTY

    var lore: Component? = null
    var wikiUrl: String? = null

    private val rules = mutableListOf<Rule>()
    private val tokens = mutableListOf<Token>()

    fun rule(rule: Rule) {
        rules += rule
    }

    fun token(icon: ItemStack, name: Component) {
        tokens += Token(icon, name)
    }

    fun token(icon: ItemStack, name: String) {
        token(icon, Component.literal(name))
    }

    fun token(icon: Item, name: Component) {
        token(ItemStack(icon), name)
    }

    fun token(icon: Item, name: String) {
        token(icon, Component.literal(name))
    }

    fun buildFabled() = Fabled(
        name = name,
        description = description,
        icon = icon,
        lore = lore,
        wikiUrl = wikiUrl,
        tokens = tokens.toList(),
        rules = rules.toList()
    )

    fun buildLoric() = Loric(
        name = name,
        description = description,
        icon = icon,
        lore = lore,
        wikiUrl = wikiUrl,
        tokens = tokens.toList(),
        rules = rules.toList()
    )
}
