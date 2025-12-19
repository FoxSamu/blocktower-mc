package dev.runefox.blocktower.common.util

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style

operator fun String.unaryPlus(): MutableComponent {
    return Component.literal(this)
}

operator fun Component?.unaryPlus(): MutableComponent {
    return this?.copy() ?: Component.empty()
}

operator fun Style.unaryPlus(): MutableComponent {
    return Component.empty().setStyle(this)
}

operator fun Component?.plus(literal: String): MutableComponent {
    return (+this).append(literal)
}

operator fun Component?.plus(component: Component): MutableComponent {
    return (+this).append(component)
}

operator fun Component?.plus(style: Style): MutableComponent {
    return (+this).withStyle(style)
}

operator fun Component?.plus(style: MapStyle): MutableComponent {
    return (+this).withStyle { style.apply(it) }
}

operator fun Component?.plus(component: Any?): MutableComponent {
    return (+this).append(component.toString())
}

operator fun MutableComponent.plusAssign(literal: String) {
    append(literal)
}

operator fun MutableComponent.plusAssign(component: Component) {
    append(component)
}

operator fun MutableComponent.plusAssign(style: Style) {
    withStyle(style)
}

operator fun MutableComponent.plusAssign(style: MapStyle) {
    withStyle { style.apply(it) }
}

operator fun MutableComponent.plusAssign(component: Any?) {
    append(component.toString())
}

fun String.translate(fallback: String? = null): Formattable {
    return Formattable {
        Component.translatableWithFallback(this, fallback, *it.map { it ?: "null" }.toTypedArray())
    }
}

fun T(vararg elements: Any?): Component {
    return elements.fold(Component.empty()) { base, it ->
        when (it) {
            is String -> base.append(it)
            is Component -> base.append(it)
            is Style -> base.withStyle(it)
            is ChatFormatting -> base.withStyle(it)
            is MapStyle -> base.withStyle { s -> it.apply(s) }
            else -> base.append(it.toString())
        }
    }
}

fun interface MapStyle {
    fun apply(style: Style): Style
}

val Italic = MapStyle { it.withItalic(true) }
val Bold = MapStyle { it.withBold(true) }

fun interface Formattable {
    fun format(vararg args: Any?): Component
}
