package dev.runefox.blocktower.common.model

import com.mojang.serialization.DataResult

class Distributions(
    distributions: List<Distribution>
) {
    constructor(vararg distributions: Distribution) : this(listOf(*distributions))

    val distributions = distributions.sortedBy { it.total }

    val minPlayers = this.distributions.firstOrNull()?.total ?: 0
    val maxPlayers = this.distributions.lastOrNull()?.total ?: 0
    val playerRange = minPlayers..maxPlayers

    fun validate(): DataResult<Distributions> {
        var i = minPlayers
        for (dist in distributions) {
            return when {
                dist.total < i -> DataResult.error { "Multiple distributions for ${dist.total} players" }
                dist.total > i -> DataResult.error { "Missing distribution for $i players" }
                else -> {
                    i++
                    continue
                }
            }
        }

        return DataResult.success(this)
    }
}
