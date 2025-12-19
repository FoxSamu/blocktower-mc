package dev.runefox.blocktower.common.model

import dev.runefox.blocktower.common.util.T
import net.minecraft.core.Holder
import net.minecraft.core.HolderGetter
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

class ScriptBuilder(
    private val roleGetter: HolderGetter<Role>
) {
    var name = T()
    var description = T()

    var lore: Component? = null
    var wikiUrl: String? = null

    val roles = RolesBuilder()
    val firstNight = NightOrderBuilder()
    val otherNights = NightOrderBuilder()
    val distributions = DistributionsBuilder()

    private val tokens = mutableListOf<Token>()


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

    inline fun roles(builder: RolesBuilder.() -> Unit) {
        roles.builder()
    }

    inline fun firstNight(builder: NightOrderBuilder.() -> Unit) {
        firstNight.builder()
    }

    inline fun otherNights(builder: NightOrderBuilder.() -> Unit) {
        otherNights.builder()
    }

    inline fun allNights(builder: NightOrderBuilder.() -> Unit) {
        firstNight.builder()
        otherNights.builder()
    }

    inline fun distributions(builder: DistributionsBuilder.() -> Unit) {
        distributions.builder()
    }

    fun build() = Script(
        name = name,
        description = description,
        lore = lore,
        roles = Roles(roles.entries),
        nightOrder = NightMap(
            NightOrder(firstNight.entries),
            NightOrder(otherNights.entries),
        ),
        distributions = Distributions(
            distributions.entries
        ),
        tokens = tokens.toList()
    )

    inner class RolesBuilder {
        val entries = mutableListOf<Reference<Holder<Role>>>()

        operator fun ResourceKey<Role>.invoke() {
            entries += Reference(identifier().path, roleGetter.getOrThrow(this))
        }

        operator fun ResourceKey<Role>.invoke(name: String) {
            entries += Reference(name, roleGetter.getOrThrow(this))
        }
    }

    inner class NightOrderBuilder {
        val entries = mutableListOf<NightOrder.Entry>()

        operator fun String.unaryPlus() {
            entries += NightOrder.Main(this)
        }

        operator fun String.unaryMinus() {
            entries += NightOrder.Ignore(this)
        }

        operator fun ResourceKey<Role>.unaryPlus() {
            entries += NightOrder.Main(identifier().path)
        }

        operator fun ResourceKey<Role>.unaryMinus() {
            entries += NightOrder.Ignore(identifier().path)
        }

        inline operator fun String.invoke(rule: () -> Rule) {
            entries += NightOrder.Override(this, rule())
        }

        inline operator fun ResourceKey<Role>.invoke(rule: () -> Rule) {
            entries += NightOrder.Override(identifier().path, rule())
        }
    }

    inner class DistributionsBuilder {
        val entries = mutableListOf<Distribution>()

        fun include(townsfolk: Int, outsiders: Int, minions: Int, demons: Int) {
            entries += Distribution(townsfolk, outsiders, minions, demons)
        }
    }
}
