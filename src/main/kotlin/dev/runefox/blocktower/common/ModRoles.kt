package dev.runefox.blocktower.common

import dev.runefox.blocktower.common.util.DynamicDataHolder
import dev.runefox.blocktower.common.model.Role
import dev.runefox.blocktower.common.model.RoleType
import dev.runefox.blocktower.common.model.Rule
import dev.runefox.blocktower.common.model.Token
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

object ModRoles : DynamicDataHolder<Role>(ModRegistries.ROLE) {
    // Townsfolk
    val WASHERWOMAN by this
    val LIBRARIAN by this
    val INVESTIGATOR by this
    val CHEF by this
    val EMPATH by this
    val FORTUNE_TELLER by this
    val UNDERTAKER by this
    val MONK by this
    val RAVENKEEPER by this
    val VIRGIN by this
    val SLAYER by this
    val SOLDIER by this
    val MAYOR by this

    // Outsiders
    val BUTLER by this
    val SAINT by this
    val RECLUSE by this
    val DRUNK by this

    // Minions
    val POISONER by this
    val SPY by this
    val BARON by this
    val SCARLET_WOMAN by this

    // Demons
    val IMP by this

    override fun register(entries: BootstrapContext<Role>) {
        entries.register(WASHERWOMAN, townsfolk(
            "Washerwoman",
            "You start knowing that 1 of 2 people is a particular townsfolk.",
            ModItems.CLOTHESLINE
        ))

        entries.register(LIBRARIAN, townsfolk(
            "Librarian",
            "You start knowing that 1 of 2 people is a particular outsider (or that none are in play).",
            Items.BOOK
        ))

        entries.register(INVESTIGATOR, townsfolk(
            "Investigator",
            "You start knowing that 1 of 2 people is a particular minion.",
            ModItems.MAGNIFYING_GLASS
        ))

        entries.register(CHEF, townsfolk(
            "Chef",
            "You start knowing how many pairs of evil players there are.",
            ModItems.CHEF_HAT
        ))

        entries.register(EMPATH, townsfolk(
            "Empath",
            "Each night, you learn how many of your 2 alive neighbours are evil.",
            ModItems.EMPATHY
        ))

        entries.register(FORTUNE_TELLER, townsfolk(
            "Fortune Teller",
            "Each night, choose two players: you'll learn if either is a demon. There is a good player that registers as a demon to you.",
            ModItems.GLASS_ORB
        ).copy(tokens = listOf(
            Token(ItemStack(Items.SALMON), Component.literal("Red Herring"))
        )))

        entries.register(UNDERTAKER, townsfolk(
            "Undertaker",
            "Each night, except the first, you learn which character died by execution the day before.",
            ModItems.BIG_SHOVEL
        ))

        entries.register(MONK, townsfolk(
            "Monk",
            "Each night, except the first, choose a player (but not yourself): they are safe from the demon tonight.",
            ModItems.CROSS
        ))

        entries.register(RAVENKEEPER, townsfolk(
            "Ravenkeeper",
            "If you die at night, you are woken to choose a player: you will learn their role.",
            ModItems.RAVEN
        ))

        entries.register(VIRGIN, townsfolk(
            "Virgin",
            "The first time you are nominated, if the nominator is a townsfolk, they will die immediately.",
            ModItems.RING
        ))

        entries.register(SLAYER, townsfolk(
            "Slayer",
            "Once per game, during the day, publicly choose a player: if they are a demon, they die.",
            Items.CROSSBOW
        ))

        entries.register(SOLDIER, townsfolk(
            "Soldier",
            "You are safe from the demon.",
            Items.SHIELD
        ))

        entries.register(MAYOR, townsfolk(
            "Mayor",
            "If only three players remain, and no execution occurs, your team wins. If you die at night, another player may die instead.",
            ModItems.COURTHOUSE
        ).copy(
            rules = listOf(
                Rule(Component.literal("Another player may die."), Component.literal("If killed at night"))
            )
        ))

        entries.register(BUTLER, outsider(
            "Butler",
            "Each night, choose a player (but not yourself): the next day you may only vote if they are voting too.",
            ModItems.BELL_JAR
        ))

        entries.register(DRUNK, outsider(
            "Drunk",
            "You do not know that you are the drunk. You think you are a townsfolk character, but you are not.",
            ModItems.BEER
        ).copy(tokens = listOf(
            Token(ItemStack(ModItems.BEER), Component.literal("Is Drunk"))
        )))

        entries.register(RECLUSE, outsider(
            "Recluse",
            "You might register as a minion or demon, even if you're dead.",
            ModItems.GAS_LAMP
        ))

        entries.register(SAINT, outsider(
            "Saint",
            "If you die by execution, your team loses.",
            ModItems.SAINT_WINGS
        ))

        entries.register(POISONER, minion(
            "Poisoner",
            "Each night, choose a player: they are poisoned tonight and tomorrow.",
            ModItems.POISON_BOTTLE
        ).copy(tokens = listOf(
            Token(ItemStack(ModItems.POISON_BOTTLE), Component.literal("Poisoned"))
        )))

        entries.register(SPY, minion(
            "Spy",
            "Each night, you see the grimoire. You might register as a townsfolk or outsider, even if dead.",
            ModItems.MASK
        ))

        entries.register(SCARLET_WOMAN, minion(
            "Scarlet Woman",
            "If there are 5 or more player alive (not counting travellers), and the demon dies, then you become the demon.",
            ModItems.KISS
        ))

        entries.register(BARON, minion(
            "Baron",
            "There are 2 extra outsiders in play.",
            ModItems.TOP_HAT
        ))

        entries.register(IMP, demon(
            "Imp",
            "Each night, except the first, choose a player: they die. If you kill yourself this way, a minion becomes the Imp.",
            ModItems.IMP_TRIDENT
        ))
    }

    private fun townsfolk(name: String, desc: String, icon: Item) = Role(
        RoleType.TOWNSFOLK,
        Component.literal(name),
        Component.literal(desc),
        ItemStack(icon)
    )

    private fun outsider(name: String, desc: String, icon: Item) = Role(
        RoleType.OUTSIDER,
        Component.literal(name),
        Component.literal(desc),
        ItemStack(icon)
    )

    private fun minion(name: String, desc: String, icon: Item) = Role(
        RoleType.MINION,
        Component.literal(name),
        Component.literal(desc),
        ItemStack(icon)
    )

    private fun demon(name: String, desc: String, icon: Item) = Role(
        RoleType.DEMON,
        Component.literal(name),
        Component.literal(desc),
        ItemStack(icon)
    )
}
