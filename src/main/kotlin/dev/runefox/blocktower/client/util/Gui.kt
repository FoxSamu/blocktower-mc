package dev.runefox.blocktower.client.util

import net.minecraft.client.gui.components.Button
import net.minecraft.network.chat.Component

inline fun button(title: Component, crossinline click: () -> Unit, builder: Button.Builder.() -> Unit): Button {
    return Button.Builder(title) { click() }.apply { builder() }.build()
}
