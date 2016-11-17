package model

import java.util.*

class RuleUsageCounter {
    private val ruleCounter = LinkedHashMap<String, Int>()

    fun addRules(rules: List<String>) {
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
        ruleCounter.forEach { s, i -> System.out.println(i.toString() + " " + s) }
        println(ruleCounter.size)
    }
}
