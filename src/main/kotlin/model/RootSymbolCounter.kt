package model

class RootSymbolCounter(trees: MutableList<Node>) {

    val rootTable: Counter<String> = Counter()
    val counter: Int
        get() = rootTable.sum

    init {
        trees.forEach { addRootSymbol(it.symbol) }
    }

    fun addRootSymbol(symbol: String) {
        rootTable.addValue(symbol)
    }

    fun getCounterFor(symbol: String): Int {
        return rootTable.getCounterFor(symbol)
    }

    fun evaluateProbabilityOf(symbol: String): Double {
        return getCounterFor(symbol).toDouble() / rootTable.sum.toDouble()
    }

    fun print() {
        println("q_")
        rootTable.values.forEach { println("q_ -> q_$it # ${evaluateProbabilityOf(it)}") }
    }
}