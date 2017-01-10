package model

import java.util.*

class RegularRuleCollector(trees: MutableList<Node>) {

    var regularRules: MutableMap<RegularRule, Int>

    init {
        regularRules = HashMap<RegularRule, Int>()
        trees.forEach { putRules(it.regularProductionsForWholeTree) }
        addNewStartsymbol()
        addRulesForStartsymbol(trees)
    }

    fun putRules(rules: Set<RegularRule>) {
        rules.forEach { putRule(it) }
    }

    fun putRule(rule: RegularRule) {
        val counter = regularRules[rule] ?: 0
        regularRules[rule] = counter + 1
    }

    private fun addNewStartsymbol() {
        putRule(RegularRule("", "", ""))
    }

    private fun addRulesForStartsymbol(trees: MutableList<Node>) {
        trees.forEach { putRule(RegularRule("", Node.SEPARATOR_ARROW, " ${it.symbol}")) }
    }

    val size: Int
        get() = sortedRegularRules.size

    fun print() {
        regularRules.toSortedMap().forEach { println("${it.key} # ${getProductionWeightFor(it.key)}") }
    }

    fun getCountFor(rule: RegularRule): Int {
        return regularRules[rule] ?: 0
    }

    fun getRulesForSymbol(symbol: String): Map<RegularRule, Int> {
        return regularRules.toSortedMap().filter { it.key.symbol == symbol }
    }

    fun getCounterSumForSymbol(symbol: String): Int {
        return getRulesForSymbol(symbol).values.sum()
    }

    fun getProductionWeightFor(rule: RegularRule): Double {
        val result = getCountFor(rule).toDouble() / getCounterSumForSymbol(rule.symbol).toDouble()
        if (Double.NaN.compareTo(result) == 0) return 0.0
        return result
    }

}
