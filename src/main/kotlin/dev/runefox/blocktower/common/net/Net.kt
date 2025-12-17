package dev.runefox.blocktower.common.net

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry

open class Net {
    companion object {
        init {
            PayloadTypeRegistry.playS2C().register(ShowScriptPayload.TYPE, ShowScriptPayload.STREAM_CODEC)
        }
    }
}
