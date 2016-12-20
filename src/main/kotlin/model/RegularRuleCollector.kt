package model

import java.util.*

class RegularRuleCollector(trees: MutableList<Node>) {

    val sortedRegularRules: SortedSet<RegularRule>

    init {
        val regularRules = LinkedHashSet<RegularRule>()
        trees.forEach { regularRules.addAll(it.regularProductionsForWholeTree) }
        trees.forEach { regularRules.add(RegularRule("", Node.SEPARATOR_ARROW, it.symbol)) }
        regularRules.add(RegularRule("", "", ""))
        sortedRegularRules = regularRules.toSortedSet()
    }

    val size: Int
        get() {
            return sortedRegularRules.size
        }

    fun print() {
        sortedRegularRules.forEach { println(it) }
    }

}
