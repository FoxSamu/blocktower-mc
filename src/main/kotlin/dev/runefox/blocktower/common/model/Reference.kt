package dev.runefox.blocktower.common.model

data class Reference<T>(
    val alias: String,
    val value: T
)
