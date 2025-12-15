package dev.runefox.blocktower

import net.minecraft.core.Holder
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey

object ModScripts : DynamicDataHolder<Script>(ModRegistries.SCRIPT) {
    val TROUBLE_BREWING by this

    override fun register(entries: BootstrapContext<Script>) {
        entries.register(
            TROUBLE_BREWING,
            Script(
                name = Component.literal("Trouble Brewing"),
                description = Component.literal("Trouble Brewing has a little bit of everything. Some characters passively receive information, some need to take action to learn who is who, while some simply want to bait the Demon into attacking them. Both good and evil can gain the upper hand by making well-timed sacrifices. Trouble Brewing is a relatively straightforward Demon-hunt, but evil has a number of dastardly misinformation tricks up their sleeves, so the good players best question what they think they know if they hope to survive."),
                roles = entries.roles(
                    ModRoles.WASHERWOMAN,
                    ModRoles.LIBRARIAN,
                    ModRoles.INVESTIGATOR,
                    ModRoles.CHEF,
                    ModRoles.EMPATH,
                    ModRoles.FORTUNE_TELLER,
                    ModRoles.UNDERTAKER,
                    ModRoles.MONK,
                    ModRoles.RAVENKEEPER,
                    ModRoles.VIRGIN,
                    ModRoles.SLAYER,
                    ModRoles.SOLDIER,
                    ModRoles.MAYOR,
                    ModRoles.BUTLER,
                    ModRoles.SAINT,
                    ModRoles.RECLUSE,
                    ModRoles.DRUNK,
                    ModRoles.POISONER,
                    ModRoles.SPY,
                    ModRoles.BARON,
                    ModRoles.SCARLET_WOMAN,
                    ModRoles.IMP,
                ),
                setupOrder = listOf(
                    Rule(Component.literal("Assign who is the Drunk."), Component.literal("If the Drunk is in play")),
                    Rule(Component.literal("Assign who is the red herring."), Component.literal("If the Fortune Teller is in play")),
                    Rule(Component.literal("Tell each minion who is the Imp."), Component.literal("If 7 or more players")),
                    Rule(Component.literal("Tell the Imp who the minions are."), Component.literal("If 7 or more players")),
                    Rule(Component.literal("Tell the Imp 3 characters not in play as bluffs."), Component.literal("If 7 or more players")),
                ),
                firstNightOrder = listOf(
                    Action("poisoner", Component.literal("Picks a player to poison.")),
                    Action("washerwoman", Component.literal("Receives a townsfolk role in play and two players of which one is that townsfolk.")),
                    Action("librarian", Component.literal("Receives an outsider role in play and two players of which one is that outsider.")),
                    Action("investigator", Component.literal("Receives a minion role in play and two players of which one is that minion.")),
                    Action("chef", Component.literal("Receives number of pairs of neighbouring evil players.")),
                    Action("empath", Component.literal("Receives the number of evil alive neighbours (0, 1, or 2).")),
                    Action("fortune_teller", Component.literal("Picks two players and learns if either of them is a demon or red herring.")),
                    Action("butler", Component.literal("Picks a player to become their master.")),
                    Action("spy", Component.literal("Gets to see the grimoire.")),
                ),
                nightOrder = listOf(
                    Action("poisoner", Component.literal("Picks a player to poison.")),
                    Action("monk", Component.literal("Picks a player to protect (not themself).")),
                    Action("scarlet_woman", Component.literal("Becomes the imp."), Component.literal("If demon died last day")),
                    Action("imp", Component.literal("Picks a player to kill.")),
                    Action("imp", Component.literal("Moves to a minion."), Component.literal("If demon killed themself")),
                    Action("ravenkeeper", Component.literal("Picks a player and receives their role."), Component.literal("If killed this night")),
                    Action("empath", Component.literal("Receives the number of evil alive neighbours (0, 1, or 2).")),
                    Action("fortune_teller", Component.literal("Picks two players and learns if either of them is a demon or red herring.")),
                    Action("undertaker", Component.literal("Learns the role of the executed player."), Component.literal("If a player was executed")),
                    Action("butler", Component.literal("Picks a player to become their master.")),
                    Action("spy", Component.literal("Gets to see the grimoire.")),
                ),
                minPlayers = 5,
                distribution = listOf(
                    Distribution(3, 0, 1, 1),
                    Distribution(3, 1, 1, 1),
                    Distribution(5, 0, 1, 1),
                    Distribution(5, 1, 1, 1),
                    Distribution(5, 2, 1, 1),
                    Distribution(7, 0, 2, 1),
                    Distribution(7, 1, 2, 1),
                    Distribution(7, 2, 2, 1),
                    Distribution(9, 0, 3, 1),
                    Distribution(9, 1, 3, 1),
                    Distribution(9, 2, 3, 1),
                )
            )
        )
    }

    private fun BootstrapContext<*>.roles(vararg roles: ResourceKey<Role>): Map<String, Holder<Role>> {
        return roles.associate {
            it.identifier().path to lookup(it.registryKey()).getOrThrow(it)
        }
    }
}
