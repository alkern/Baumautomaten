package model

import java.util.*

class RuleUsageCounter() {

    private val ruleCounter: HashMap<String, Int>

    init {
        ruleCounter = LinkedHashMap<String, Int>()
    }

    constructor(trees: List<Node>) : this() {
        trees.map { n -> n.productionsWithDuplicates }
                .forEach { l -> addRules(l) }
    }


    fun addRules(rules: MutableList<String>) {
        rules.forEach { s -> addRule(s) }
    }

    fun addRule(rule: String) {
        if (ruleCounter.containsKey(rule)) {
            val count = ruleCounter[rule]
            ruleCounter[rule] = Integer.valueOf(count!!.plus(1))
            return
        }
        ruleCounter[rule] = 1
    }

    fun getCountFor(rule: String): Int {
        return ruleCounter[rule] ?: 0
    }

    fun print() {
        ruleCounter.forEach { s, i -> System.out.println(formatRule(s, i)) }
        println(ruleCounter.size)
    }

    fun asStringList(): MutableList<String> {
        val productions = LinkedList<String>()
        ruleCounter.forEach { s, i -> productions.add(formatRule(s, i)) }
        return productions
    }

    private fun formatRule(rule: String, counter: Int): String {
        return (counter.toString() + " " + rule)
    }
}

