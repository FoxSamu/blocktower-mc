package dev.runefox.blocktower.common.model

import dev.runefox.blocktower.common.util.SerialEnum
import dev.runefox.blocktower.common.util.enumCodec

enum class Alignment(override val serialName: String) : SerialEnum {
    GOOD("good"),
    EVIL("evil");

    companion object {
        val CODEC = enumCodec<Alignment>()
    }
}
