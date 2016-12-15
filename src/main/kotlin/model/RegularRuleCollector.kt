package model

import java.util.*

class RegularRuleCollector(trees: MutableList<Node>) {

    val sortedRegularRules: SortedSet<String>

    init {
        val regularRules = LinkedHashSet<String>()
        trees.forEach { regularRules.addAll(it.regularProductionsForWholeTree) }
        trees.forEach { regularRules.add("q_ -> ${it.symbol}") }
        regularRules.add("q_")
        sortedRegularRules = regularRules.toSortedSet()
    }

    val size: Int
        get() = sortedRegularRules.size

    fun print() {
        sortedRegularRules.forEach { println(it) }
    }
}
