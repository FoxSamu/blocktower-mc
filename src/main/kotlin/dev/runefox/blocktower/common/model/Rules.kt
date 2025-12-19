package dev.runefox.blocktower.common.model

class Rules : References<Rule> {
    constructor(references: List<Reference<Rule>>) : super(references)
    constructor(byAlias: Map<String, Rule>) : super(byAlias)
    constructor(main: Rule) : super(listOf(Reference("main", main)))
    constructor() : super(listOf())

    val main get() = values.single()
}
