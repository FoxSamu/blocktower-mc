package dev.runefox.blocktower.common.model

import com.mojang.serialization.DataResult
import net.minecraft.core.Holder

class Roles(
    references: List<Reference<Holder<Role>>>
) : References<Holder<Role>>(references) {
    // These have to be lazily loaded so we don't accidentally call Holder.Reference.value() during datagen
    val townsfolk by lazy { references.filter { (_, role) -> role.value().type == RoleType.TOWNSFOLK } }
    val outsiders by lazy { references.filter { (_, role) -> role.value().type == RoleType.OUTSIDER } }
    val minions by lazy { references.filter { (_, role) -> role.value().type == RoleType.MINION } }
    val demons by lazy { references.filter { (_, role) -> role.value().type == RoleType.DEMON } }
}
