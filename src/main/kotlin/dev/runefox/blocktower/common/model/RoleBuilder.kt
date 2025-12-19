package dev.runefox.blocktower.common.model

import dev.runefox.blocktower.common.util.T
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

class RoleBuilder(
    private val type: RoleType,
) {
    var name: Component = T()
    var description: Component = T()
    var icon: ItemStack = ItemStack.EMPTY

    var lore: Component? = null
    var alignmentOverride: Alignment? = null
    var wikiUrl: String? = null

    private var firstNightRule: Rule? = null
    private var otherNightsRule: Rule? = null

    private val tokens = mutableListOf<Token>()

    @Deprecated("Use `name` field")
    fun name(name: Component) {
        this.name = name
    }

    @Deprecated("Use `name` field")
    fun name(name: String) {
        this.name = Component.literal(name)
    }

    @Deprecated("Use `description` field")
    fun description(description: Component) {
        this.description = description
    }

    @Deprecated("Use `description` field")
    fun description(description: String) {
        this.description = Component.literal(description)
    }

    @Deprecated("Use `lore` field")
    fun lore(lore: Component) {
        this.lore = lore
    }

    @Deprecated("Use `lore` field")
    fun lore(lore: String) {
        this.lore = Component.literal(lore)
    }

    @Deprecated("Use `icon` field")
    fun icon(item: ItemStack) {
        icon = item
    }

    @Deprecated("Use `icon` field")
    fun icon(item: Item) {
        icon = ItemStack(item)
    }

    @Deprecated("Use `good` field")
    fun forceGood() {
        alignmentOverride = Alignment.GOOD
    }

    @Deprecated("Use `evil` field")
    fun forceEvil() {
        alignmentOverride = Alignment.EVIL
    }

    @Deprecated("Use `wikiUrl` field")
    fun wikiUrl(url: String) {
        wikiUrl = url
    }

    fun firstNight(rule: Rule) {
        firstNightRule = rule
    }

    fun otherNights(rule: Rule) {
        otherNightsRule = rule
    }

    fun allNights(rule: Rule) {
        firstNightRule = rule
        otherNightsRule = rule
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

    fun build() = Role(
        type = type,
        name = name,
        description = description,
        icon = icon,
        lore = lore,
        alignmentOverride = alignmentOverride,
        wikiUrl = wikiUrl,
        nightRule = NightMap(firstNightRule, otherNightsRule),
        tokens = tokens.toList()
    )
}
