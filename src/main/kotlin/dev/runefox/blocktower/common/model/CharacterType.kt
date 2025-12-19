package dev.runefox.blocktower.common.model

import dev.runefox.blocktower.common.util.SerialEnum
import dev.runefox.blocktower.common.util.enumCodec

enum class CharacterType(override val serialName: String) : SerialEnum {
    FABLED("fabled"),
    LORIC("loric");
}
