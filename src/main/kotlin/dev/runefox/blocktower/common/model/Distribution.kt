package dev.runefox.blocktower.common.model

data class Distribution(
    val townsfolk: Int,
    val outsiders: Int,
    val minions: Int,
    val demons: Int
) {
    val total = townsfolk + outsiders + minions + demons
}
