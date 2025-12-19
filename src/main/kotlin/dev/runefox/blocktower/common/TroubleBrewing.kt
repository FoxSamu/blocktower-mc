package dev.runefox.blocktower.common

import dev.runefox.blocktower.common.model.ScriptBuilder
import dev.runefox.blocktower.common.util.flatten
import dev.runefox.blocktower.common.util.plus

fun ScriptBuilder.troubleBrewing() {
    name += "Trouble Brewing"

    description += """
        Trouble Brewing has a little bit of everything. Some characters
        passively receive information, some need to take action to learn who is
        who, while some simply want to bait the Demon into attacking them. Both
        good and evil can gain the upper hand by making well-timed sacrifices.
        Trouble Brewing is a relatively straightforward Demon-hunt, but evil has
        a number of dastardly misinformation tricks up their sleeves, so the
        good players best question what they think they know if they hope to
        survive.
    """.flatten()

    lore += """
        Clouds roll in over Ravenswood Bluff, engulfing this sleepy town and its
        superstitious inhabitants in foreboding shadow. Freshly-washed clothes
        dance eerily on lines strung between cottages. Chimneys cough plumes of
        smoke into the air. Exotic scents waft through cracks in windows and
        under doors, as hidden cauldrons lay bubbling. An unusually warm Autumn
        breeze wraps around vine-covered walls and whispers ominously to those
        brave enough to walk the cobbled streets.

        Anxious mothers call their children home from play, as thunder begins to
        clap on the horizon. If you listen more closely, however, noises
        stranger still can be heard echoing from the neighbouring forest. Under
        the watchful eye of a looming monastery, silhouetted figures skip from
        doorway to doorway. Those who can read the signs know there is...
        Trouble Brewing.
    """.flatten()

    wikiUrl = "https://wiki.bloodontheclocktower.com/Trouble_Brewing"

    roles {
        ModRoles.WASHERWOMAN()
        ModRoles.LIBRARIAN()
        ModRoles.INVESTIGATOR()
        ModRoles.CHEF()
        ModRoles.EMPATH()
        ModRoles.FORTUNE_TELLER()
        ModRoles.UNDERTAKER()
        ModRoles.MONK()
        ModRoles.RAVENKEEPER()
        ModRoles.VIRGIN()
        ModRoles.SLAYER()
        ModRoles.SOLDIER()
        ModRoles.MAYOR()
        ModRoles.BUTLER()
        ModRoles.SAINT()
        ModRoles.RECLUSE()
        ModRoles.DRUNK()
        ModRoles.POISONER()
        ModRoles.SPY()
        ModRoles.BARON()
        ModRoles.SCARLET_WOMAN()
        ModRoles.IMP()
    }

    firstNight {
        +ModRoles.POISONER
        +ModRoles.WASHERWOMAN
        +ModRoles.LIBRARIAN
        +ModRoles.INVESTIGATOR
        +ModRoles.CHEF
        +ModRoles.EMPATH
        +ModRoles.FORTUNE_TELLER
        +ModRoles.BUTLER
        +ModRoles.SPY
    }

    otherNights {
        +ModRoles.POISONER
        +ModRoles.MONK
        +ModRoles.SCARLET_WOMAN
        +ModRoles.IMP
        +ModRoles.RAVENKEEPER
        +ModRoles.EMPATH
        +ModRoles.FORTUNE_TELLER
        +ModRoles.UNDERTAKER
        +ModRoles.BUTLER
        +ModRoles.SPY
    }

    distributions {
        include(3, 0, 1, 1)
        include(3, 1, 1, 1)
        include(5, 0, 1, 1)
        include(5, 1, 1, 1)
        include(5, 2, 1, 1)
        include(7, 0, 2, 1)
        include(7, 1, 2, 1)
        include(7, 2, 2, 1)
        include(9, 0, 3, 1)
        include(9, 1, 3, 1)
        include(9, 2, 3, 1)
    }
}
