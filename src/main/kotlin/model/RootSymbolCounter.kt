package model

import java.util.*

class RootSymbolCounter(trees: MutableList<Node>) {

    val rootTable: MutableMap<String, Int>
    var treeCounter: Int

    init {
        rootTable = LinkedHashMap<String, Int>()
        treeCounter = 0
        trees.forEach { addRootSymbol(it.symbol) }
    }

    fun addRootSymbol(symbol: String) {
        rootTable[symbol] = getCounterFor(symbol) + 1
        treeCounter++
    }

    fun getCounterFor(symbol: String): Int {
        return rootTable[symbol] ?: 0
    }

    fun evaluateProbabilityOf(symbol: String): Double {
        assert(rootTable.values.sum() == treeCounter)
        return getCounterFor(symbol).toDouble() / treeCounter.toDouble()
    }

    fun print() {
        println("q_")
        rootTable.keys.forEach { println("q_ -> q_$it # ${evaluateProbabilityOf(it)}") }
    }
}