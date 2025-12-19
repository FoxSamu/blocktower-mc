package dev.runefox.blocktower.common.model

import com.mojang.serialization.DataResult

open class References<T>(
    val references: List<Reference<T>>
) : Iterable<T> {
    constructor(byAlias: Map<String, T>) : this(byAlias.entries.map { (k, v) -> Reference(k, v) })

    val values = references.map { (_, value) -> value }

    val byAlias = references.associate { (alias, value) -> alias to value }

    operator fun get(alias: String): T {
        return byAlias[alias] ?: throw NoSuchElementException("No value aliased '$alias'")
    }

    operator fun contains(alias: String): Boolean {
        return alias in byAlias
    }

    override fun iterator(): Iterator<T> {
        return values.iterator()
    }

    fun validate(): DataResult<References<T>> {
        val aliases = mutableSetOf<String>()

        for (role in references) {
            if (!aliases.add(role.alias)) {
                return DataResult.error { "Duplicate alias: ${role.alias}" }
            }
        }

        return DataResult.success(this)
    }
}
