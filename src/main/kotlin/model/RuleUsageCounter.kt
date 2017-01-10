package model

import java.util.*

class RuleUsageCounter(trees: MutableList<Node>) {

    private val ruleCounter: Counter<String> = Counter()

    init {
        trees.map { it.productionsWithDuplicates }
                .forEach { addRules(it) }
    }

    val size: Int
        get() = ruleCounter.size

    fun addRules(rules: MutableList<String>) {
        rules.forEach { addRule(it) }
    }

    fun addRule(rule: String) {
        ruleCounter.addValue(rule)
    }

    fun getCounterFor(rule: String): Int {
        return ruleCounter.getCounterFor(rule)
    }

    fun asStringList(): MutableList<String> {
        val productions = LinkedList<String>()
        ruleCounter.sorted.forEach { productions.add(formatRule(it.key, it.value)) }
        return productions
    }

    private fun formatRule(rule: String, counter: Int): String {
        return (counter.toString() + " " + rule)
    }

}

