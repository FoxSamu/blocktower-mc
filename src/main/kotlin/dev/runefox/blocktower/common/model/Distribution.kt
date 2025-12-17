package dev.runefox.blocktower.common.model

import com.mojang.serialization.Codec
import dev.runefox.blocktower.common.util.fieldOf
import dev.runefox.blocktower.common.util.forGetter
import dev.runefox.blocktower.common.util.recordCodec

data class Distribution(
    val townsfolk: Int,
    val outsiders: Int,
    val minions: Int,
    val demons: Int
) {
    companion object {
        val CODEC = recordCodec<Distribution> {
            group(
                Codec.intRange(0, 256) fieldOf "townsfolk" forGetter { townsfolk },
                Codec.intRange(0, 256) fieldOf "outsiders" forGetter { outsiders },
                Codec.intRange(0, 256) fieldOf "minions" forGetter { minions },
                Codec.intRange(0, 256) fieldOf "demons" forGetter { demons },
            ).apply(this, ::Distribution)
        }
    }
}
