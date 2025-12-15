package dev.runefox.blocktower.util

import net.minecraft.util.StringRepresentable

interface SerialEnum : StringRepresentable {
    val serialName: String

    override fun getSerializedName(): String {
        return serialName
    }
}
