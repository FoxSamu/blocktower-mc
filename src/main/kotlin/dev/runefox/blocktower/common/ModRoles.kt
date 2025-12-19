package dev.runefox.blocktower.common

import dev.runefox.blocktower.common.util.DynamicDataHolder
import dev.runefox.blocktower.common.model.Role
import dev.runefox.blocktower.common.model.RoleType
import dev.runefox.blocktower.common.model.Rule
import dev.runefox.blocktower.common.model.Token
import dev.runefox.blocktower.common.model.demon
import dev.runefox.blocktower.common.model.minion
import dev.runefox.blocktower.common.model.outsider
import dev.runefox.blocktower.common.model.townsfolk
import dev.runefox.blocktower.common.util.T
import dev.runefox.blocktower.common.util.flatten
import dev.runefox.blocktower.common.util.get
import dev.runefox.blocktower.common.util.plus
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
        entries.register(WASHERWOMAN, townsfolk {
            name += "Washerwoman"

            description += """
                The first night, you learn that one of two people is a
                particular townsfolk.
            """.flatten()

            lore += """
                Bloodstains on a dinner jacket? No, this is cooking sherry. How
                careless.
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Washerwoman"

            icon = ModItems.CLOTHESLINE[1]
        })

        entries.register(LIBRARIAN, townsfolk {
            name += "Librarian"

            description += """
                The first night, you learn that one of two people is a
                particular outsider, or that no outsiders are in play.
            """.flatten()

            lore += """
                Certainly madam, under normal circumstances, you may borrow the
                Codex Malificarium from the library vaults. However, you do not
                seem to be a member.
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Librarian"

            icon = Items.BOOK[1]
        })

        entries.register(INVESTIGATOR, townsfolk {
            name += "Investigator"

            description += """
                The first night, you learn that one of two people is a
                particular minion role.
            """.flatten()

            lore += """
                It is a fine night for a stroll, wouldn't you say, Mister
                Morozov? Or should I say... BARON Morozov?
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Investigator"

            icon = ModItems.MAGNIFYING_GLASS[1]
        })

        entries.register(CHEF, townsfolk {
            name += "Chef"

            description += """
                The first night, you learn how many pairs of evil players there
                are. Each two evil players sitting next to eachother.
            """.flatten()

            lore += """
                This evening's reservations seem odd. Never before has Mrs
                Mayweather kept company with that scamp from Hudson Lane. Yet,
                tonight, they have a table for two. Strange.
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Chef"

            icon = ModItems.CHEF_HAT[1]
        })

        entries.register(EMPATH, townsfolk {
            name += "Empath"

            description += """
                Each night, you learn how many of your two alive neighbours are
                evil.
            """.flatten()

            lore += """
                My skin prickles. Something is not right here. I can feel it.
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Empath"

            icon = ModItems.EMPATHY[1]
        })

        entries.register(FORTUNE_TELLER, townsfolk {
            name += "Fortune Teller"

            description += """
                Each night, choose two players: you'll learn if either is a
                demon. There is a good player that registers as a demon to you.
            """.flatten()

            lore += """
                I sense great evil in your soul! But... that could just be your
                perfume. I am allergic to Elderberry.
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Fortune_Teller"

            icon = ModItems.GLASS_ORB[1]

            token(Items.SALMON, "Red Herring")
        })

        entries.register(UNDERTAKER, townsfolk {
            name += "Undertaker"

            description += """
                Each night, after any execution, you learn the role of the
                executed player.
            """.flatten()

            lore += """
                Hmmm....what have we here? The left boot is worn down to the
                heel, with flint shavings under the tongue. This is the garb of
                a Military man.
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Undertaker"

            icon = ModItems.BIG_SHOVEL[1]
        })

        entries.register(MONK, townsfolk {
            name += "Monk"

            description += """
                Each night, except the first night, choose a player other than
                yourself. The chosen player is safe from the demon tonight.
            """.flatten()

            lore += """
                Tis an ill and deathly wind that blows tonight. Come, my
                brother, take shelter in the abbey while the storm rages. By my
                word, or by my life, you will be safe.
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Monk"

            icon = ModItems.CROSS[1]
        })

        entries.register(RAVENKEEPER, townsfolk {
            name += "Ravenkeeper"

            description += """
                If you die at night, you are woken to choose a player: you will
                learn their role.
            """.flatten()

            lore += """
                My birds will avenge me! Fly! Fly, my sweet and dutiful pets! To
                the manor and to the river! To the alleys and to the salons!
                Fly!
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Ravenkeeper"

            icon = ModItems.RAVEN[1]
        })

        entries.register(VIRGIN, townsfolk {
            name += "Virgin"

            description += """
                The first time you are nominated, if the nominator is a
                townsfolk, they will die immediately.
            """.flatten()

            lore += """
                I am pure. Let those who are without sin cast themselves down
                and suffer in my stead. My reputation shall not be stained with
                your venomous accusations.
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Virgin"

            icon = ModItems.RING[1]
        })

        entries.register(SLAYER, townsfolk {
            name += "Slayer"

            description += """
                Once per game, during the day, publicly choose a player: if they
                are a demon, they die.
            """.flatten()

            lore += """
                Die.
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Slayer"

            icon = Items.CROSSBOW[1]
        })

        entries.register(SOLDIER, townsfolk {
            name += "Soldier"

            description += """
                You are safe from the demon.
            """.flatten()

            lore += """
                As David said to Goliath, as Theseus said to the Minotaur, as
                Arjuna said to Bhagadatta... No.
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Soldier"

            icon = Items.SHIELD[1]
        })

        entries.register(MAYOR, townsfolk {
            name += "Mayor"

            description += """
                During the day, if only three players remain, and no execution
                occurs, your team wins. If you die at night, another player may
                die instead.
            """.flatten()

            lore += """
                We must put our differences aside, and cease this senseless
                killing. We are all taxpayers after all. Well, most of us.
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Mayor"

            icon = ModItems.COURTHOUSE[1]
        })

        entries.register(BUTLER, outsider {
            name += "Butler"

            description += """
                Each night, choose a player (but not yourself): the next day you
                may only vote if they are voting too.
            """.flatten()

            lore += """
                Yes, sir...
                No, sir...
                Certainly, sir.
            """.trimIndent() // Not flattened since these are actually separated lines

            wikiUrl = "https://wiki.bloodontheclocktower.com/Butler"

            icon = ModItems.BELL_JAR[1]
        })

        entries.register(DRUNK, outsider {
            name += "Drunk"

            description += """
                You do not know that you are the drunk. You think you are a
                townsfolk character, but you are not.
            """.flatten()

            lore += """
                I’m only a *hic* social drinker, my dear. Admittedly, I am a
                heavy *burp* socializer.
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Drunk"

            icon = ModItems.BEER[1]

            token(ModItems.BEER, "Is Drunk")
        })

        entries.register(RECLUSE, outsider {
            name += "Recluse"

            description += """
                You might register as a minion or demon, even if you're dead.
            """.flatten()

            lore += """
                Garn git ya darn grub ya mitts ofma lorn yasee. Grr. Natsy
                pikkins yonder southwise ye begittin afta ya! Git! Me harvy no
                so widda licks and demmons no be fightin' hadsup ne'er ma kin.
                Git, assay!
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Recluse"

            icon = ModItems.GAS_LAMP[1]
        })

        entries.register(SAINT, outsider {
            name += "Saint"

            description += """
                If you die by execution, your team loses.
            """.flatten()

            lore += """
                Wisdom begets peace. Patience begets wisdom. Fear not, for the
                time shall come when fear too shall pass. Let us pray, and may
                the unity of our vision make saints of us all.
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Saint"

            icon = ModItems.SAINT_WINGS[1]
        })

        entries.register(POISONER, minion {
            name += "Poisoner"

            description += """
                Each night, choose a player: they are poisoned tonight and
                tomorrow.
            """.flatten()

            lore += """
                Add compound Alpha to compound Beta... NOT TOO MUCH!
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Poisoner"

            icon = ModItems.POISON_BOTTLE[1]

            token(ModItems.POISON_BOTTLE, "Poisoned")
        })

        entries.register(SPY, minion {
            name += "Spy"

            description += """
                Each night, you see the grimoire. You might register as a
                townsfolk or outsider, even if dead.
            """.flatten()

            lore += """
                Any brewmaster worth their liquor, knows no concoction pours
                trouble quicker, than one where spies seem double.
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Spy"

            icon = ModItems.MASK[1]
        })

        entries.register(SCARLET_WOMAN, minion {
            name += "Scarlet Woman"

            description += """
                If there are 5 or more player alive (not counting travellers),
                and the demon dies, then you become the demon.
            """.flatten()

            lore += """
                You have shown me the secrets of the Council of the Purple
                Flame. We have lain together in fire and in lust and in beastly
                commune, and I am forever your servant. But tonight, my dear, I
                am your master.
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Scarlet_Woman"

            icon = ModItems.KISS[1]
        })

        entries.register(BARON, minion {
            name += "Baron"

            description += """
                There are 2 extra outsiders in play.
            """.flatten()

            lore += """
                This town has gone to the dogs, what? Cheap foreign labor...
                that's the ticket. Stuff them in the mine, I say. A bit of hard
                work never hurt anyone, and a clip'o'the ears to any brigand who
                says otherwise. It's all about the bottom line, what?
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Baron"

            icon = ModItems.TOP_HAT[1]
        })

        entries.register(IMP, demon {
            name += "Imp"

            description += """
                Each night, except the first, choose a player: they die. If you
                kill yourself this way, a minion becomes the Imp.
            """.flatten()

            lore += """
                We must keep our wits sharp and our sword sharper. Evil walks
                among us, and will stop at nothing to destroy us good, simple
                folk, bringing our fine town to ruin. Trust no-one. But, if you
                must trust someone, trust me.
            """.flatten()

            wikiUrl = "https://wiki.bloodontheclocktower.com/Imp"

            icon = ModItems.IMP_TRIDENT[1]
        })
    }
}
