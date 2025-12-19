package dev.runefox.blocktower.common.util

private val NEWLINES = Regex("\n+|\r")

fun String.flatten() = trimIndent().replace(NEWLINES) {
    when (val value = it.value) {
        "\r" -> "\n"
        "\n" -> " "
        else -> value
    }
}
