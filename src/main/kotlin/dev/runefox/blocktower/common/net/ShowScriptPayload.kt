package dev.runefox.blocktower.common.net

import dev.runefox.blocktower.common.ModRegistries
import dev.runefox.blocktower.common.model.Script
import dev.runefox.blocktower.common.util.blocktower
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceKey

class ShowScriptPayload(val script: ResourceKey<Script>) : CustomPacketPayload {
    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
        return TYPE
    }

    companion object {
        val TYPE = CustomPacketPayload.Type<ShowScriptPayload>(blocktower("parcel"))

        val STREAM_CODEC = ResourceKey
            .streamCodec(ModRegistries.SCRIPT)
            .map(::ShowScriptPayload, ShowScriptPayload::script)
    }
}
