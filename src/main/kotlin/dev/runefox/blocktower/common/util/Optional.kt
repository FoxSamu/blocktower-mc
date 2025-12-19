package dev.runefox.blocktower.common.util

import java.util.Optional

fun <T> Optional<T>.unwrap(): T? {
    return orElse(null)
}

inline infix fun <T> Optional<T>.or(other: () -> T): T {
    return if (isPresent) get() else other()
}
