package dev.runefox.blocktower.common.model

import net.minecraft.network.chat.Component
import java.util.*

data class Script(
    override val name: Component,
    override val description: Component,

    val roles: Roles,
    val nightOrder: NightMap<NightOrder>,
    val distributions: Distributions,

    override val lore: Component? = null,
    override val wikiUrl: String? = null,

    val tokens: List<Token> = emptyList(),
) : Described {
    constructor(
        name: Component,
        description: Component,
        roles: Roles,
        nightOrder: NightMap<NightOrder>,
        distributions: Distributions,
        lore: Optional<Component>,
        wikiUrl: Optional<String>,
        tokens: List<Token>
    ) : this(
        name,
        description,
        roles,
        nightOrder,
        distributions,
        lore.orElse(null),
        wikiUrl.orElse(null),
        tokens
    )
}
