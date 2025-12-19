package dev.runefox.blocktower.common.model

data class NightMap<T>(
    val firstNight: T,
    val otherNights: T
) {
    constructor(allNights: T) : this(allNights, allNights)

    operator fun get(night: Int): T {
        if (night < 1) {
            throw IndexOutOfBoundsException(night)
        }

        return when (night) {
            1 -> firstNight
            else -> otherNights
        }
    }
}
