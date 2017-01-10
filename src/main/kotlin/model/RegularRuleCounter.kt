package model

class RegularRuleCounter(trees: MutableList<Node>) {

    private val regularRules: Counter<RegularRule> = Counter()

    init {
        trees.forEach { putRules(it.regularProductionsForWholeTree) }
        addRulesForStartSymbol(trees)
    }

    fun putRules(rules: Set<RegularRule>) {
        rules.forEach { putRule(it) }
    }

    fun putRule(rule: RegularRule) {
        regularRules.addValue(rule)
    }

    private fun addRulesForStartSymbol(trees: MutableList<Node>) {
        trees.forEach { putRule(RegularRule("", Node.SEPARATOR_ARROW, " ${it.symbol}")) }
    }

    val size: Int
        get() = regularRules.size + 1 //for first rule q_

    fun print() {
        println("q_")
        regularRules.sorted.forEach { println("${it.key} # ${getProductionWeightFor(it.key)}") }
    }

    fun getCounterFor(rule: RegularRule): Int {
        return regularRules.getCounterFor(rule)
    }

    fun getRulesForSymbol(symbol: String): Map<RegularRule, Int> {
        return regularRules.sorted.filter { it.key.symbol == symbol }
    }

    fun getCounterSumForSymbol(symbol: String): Int {
        return getRulesForSymbol(symbol).values.sum()
    }

    fun getProductionWeightFor(rule: RegularRule): Double {
        val result = getCounterFor(rule).toDouble() / getCounterSumForSymbol(rule.symbol).toDouble()
        if (Double.NaN.compareTo(result) == 0) return 0.0
        return result
    }

}
