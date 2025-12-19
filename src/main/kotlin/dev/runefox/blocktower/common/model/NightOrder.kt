package dev.runefox.blocktower.common.model

class NightOrder(
    entries: List<Entry>
) {
    val ignore = entries
        .filter { it is Ignore }
        .map { it.roleAlias }
        .toSet()

    val order = entries.flatMap {
        when (it) {
            is Override -> listOf(it.roleAlias to it.rule)
            is Main -> listOf(it.roleAlias to null)
            else -> emptyList()
        }
    }

    val entries get() = order.map { (alias, rule) ->
        if (rule == null) {
            Main(alias)
        } else {
            Override(alias, rule)
        }
    } + ignore.map {
        Ignore(it)
    }

    sealed interface Entry {
        val roleAlias: String
    }

    class Override(
        override val roleAlias: String,
        val rule: Rule
    ) : Entry

    class Main(
        override val roleAlias: String
    ) : Entry

    class Ignore(
        override val roleAlias: String
    ) : Entry
}
