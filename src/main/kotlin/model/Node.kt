package model

import java.util.*

class Node(val symbol: String) {

    private val children: MutableList<Node>

    init {
        this.children = ArrayList<Node>()
    }

    fun addChild(child: Node) {
        children.add(child)
    }

    val countOfChildren: Int
        get() = children.size

    val isLeaf: Boolean
        get() = countOfChildren == 0

    private val isNode: Boolean
        get() = !isLeaf

    fun getChild(number: Int): Node {
        return children[number]
    }

    fun getChild(vararg numbers: Int): Node {
        var result = this
        numbers.forEach { result = result.getChild(it) }
        return result
    }

    val height: Int
        get() = (children.maxBy { it.height }?.height ?: -1) + 1

    /**
     * @param separator Trennezichen zwischen linker und rechter Seite der Produktionsregel
     * *
     * @return Produktionsregel für diesen Node
     */
    fun getProductions(separator: String): String {
        val builder = StringBuilder()
        builder.append(symbol)
        builder.append(separator)
        children.forEach { builder.append(" ${it.symbol}") }
        return builder.toString()
    }

    fun getRegularProductions(separator: String): RegularRule {
        val builder = StringBuilder()
        builder.append(" $baseCategory(")
        val joiner = StringJoiner(" ")
        children.map { it.formatAsRegularSymbol() }
                .forEach { joiner.add(it) }
        builder.append("$joiner)")
        return RegularRule(symbol, separator, builder.toString())
    }

    fun formatAsRegularSymbol(): String {
        if (isLeaf) return symbol
        return "q_$symbol"
    }

    val baseCategory: String
        get() {
            val index = symbol.indexOfFirst { it == '-' || it == '=' }
            if (index > -1) {
                return symbol.substring(0, index)
            }
            return symbol
        }

    val productionsForWholeTree: MutableSet<String>
        get() {
            val productions = LinkedHashSet<String>()
            productions.add(getProductions(SEPARATOR_ARROW))
            children.filter { it.isNode }
                    .forEach { productions.addAll(it.productionsForWholeTree) }
            return productions
        }

    val regularProductionsForWholeTree: MutableSet<RegularRule>
        get() {
            val productions = LinkedHashSet<RegularRule>()
            productions.add(getRegularProductions(SEPARATOR_ARROW))
            children.filter { it.isNode }
                    .forEach { productions.addAll(it.regularProductionsForWholeTree) }
            return productions
        }

    val productionsWithDuplicates: MutableList<String>
        get() {
            val productions = LinkedList<String>()
            productions.add(getProductions(SEPARATOR_EMPTY))
            val deviations = LinkedHashSet<Node>()
            children.filter { it.deviatesToNonterminal() }
                    .toCollection(deviations)
            deviations.forEach { productions.addAll(it.productionsWithDuplicates) }
            return productions
        }

    fun yieldOperation(): String {
        if (isLeaf) {
            return symbol + " "
        }
        val builder = StringBuilder()
        children.map { it.yieldOperation() }.forEach { builder.append(it) }
        return builder.toString()
    }

    override fun toString(): String {
        val builder = StringBuilder()

        builder.append("(")
        builder.append(symbol)

        if (isNode) {
            children.forEach { builder.append(it.toString()) }
        }

        builder.append(")")
        return builder.toString()
    }

    val isTraceTree: Boolean
        get() {
            if (this.isLeaf) return symbol.startsWith("*")
            return children.all { it.isTraceTree }
        }

    fun removeTraceTrees() {
        children.filter { !it.isTraceTree }.forEach { it.removeTraceTrees() }
        val toRemove = LinkedList<Node>()
        children.filter { it.isTraceTree }.toCollection(toRemove)
        toRemove.forEach { children.remove(it) }
    }

    fun deviatesToTerminal(): Boolean {
        return countOfChildren == 1 && getChild(0).isLeaf
    }

    private fun deviatesToNonterminal(): Boolean {
        return !deviatesToTerminal() && isNode
    }

    fun writeToLexicon(counter: WordUsageCounter) {
        children.forEach {
            if (it.isLeaf) {
                counter.addWord(it.symbol, this.symbol)
                return
            }
            it.writeToLexicon(counter)
        }
    }

    companion object {
        val SEPARATOR_ARROW = " ->"
        val SEPARATOR_EMPTY = ""
    }

}
