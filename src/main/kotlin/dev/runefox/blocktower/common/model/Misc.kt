package dev.runefox.blocktower.common.model

import dev.runefox.blocktower.common.util.MapStyle
import net.minecraft.ChatFormatting
import net.minecraft.core.HolderGetter
import net.minecraft.network.chat.Component

fun rule(description: Component): Rule {
    return Rule(description)
}

fun rule(description: String): Rule {
    return Rule(Component.literal(description))
}

infix fun Rule.onlyIf(condition: Component): Rule {
    return copy(condition = condition)
}

infix fun Rule.onlyIf(condition: String): Rule {
    return copy(condition = Component.literal(condition))
}

inline fun townsfolk(builder: RoleBuilder.() -> Unit): Role {
    return RoleBuilder(RoleType.TOWNSFOLK).apply(builder).build()
}

inline fun outsider(builder: RoleBuilder.() -> Unit): Role {
    return RoleBuilder(RoleType.OUTSIDER).apply(builder).build()
}

inline fun minion(builder: RoleBuilder.() -> Unit): Role {
    return RoleBuilder(RoleType.MINION).apply(builder).build()
}

inline fun demon(builder: RoleBuilder.() -> Unit): Role {
    return RoleBuilder(RoleType.DEMON).apply(builder).build()
}

inline fun fabled(builder: StoryCharacterBuilder.() -> Unit): Fabled {
    return StoryCharacterBuilder().apply(builder).buildFabled()
}

inline fun loric(builder: StoryCharacterBuilder.() -> Unit): Loric {
    return StoryCharacterBuilder().apply(builder).buildLoric()
}

inline fun script(roles: HolderGetter<Role>, builder: ScriptBuilder.() -> Unit): Script {
    return ScriptBuilder(roles).apply(builder).build()
}
