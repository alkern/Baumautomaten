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
        for (i in numbers) {
            result = result.getChild(i)
        }
        return result
    }

    val height: Int
        get() {
            if (isLeaf) {
                return 0
            }
            var max = 0
            children.forEach { n ->
                run {
                if (max < n.height) {
                    max = n.height
                }
                }
            }
            return 1 + max
        }

    /**
     * @param separator Trennezichen zwischen linker und rechter Seite der Produktionsregel
     * *
     * @return Produktionsregel für diesen Node
     */
    fun getProductions(separator: String): String {
        val builder = StringBuilder()
        builder.append(symbol)
        builder.append(separator)
        for (child in children) {
            builder.append(" ")
            builder.append(child.symbol)
        }
        return builder.toString()
    }

    val productionsForWholeTree: MutableSet<String>
        get() {
            val productions = LinkedHashSet<String>()
            productions.add(getProductions(SEPARATOR_ARROW))
            children.filter { n -> n.isNode }
                    .forEach { node -> productions.addAll(node.productionsForWholeTree) }
            return productions
        }

    val productionsWithDuplicates: MutableList<String>
        get() {
            val productions = LinkedList<String>()
            productions.add(getProductions(SEPARATOR_EMPTY))
            val deviations = LinkedHashSet<Node>()
            children.filter { n -> n.deviatesToNonterminal() }
                    .toCollection(deviations)
            deviations.forEach { node -> productions.addAll(node.productionsWithDuplicates) }
            return productions
        }

    fun yieldOperation(): String {
        if (isLeaf) {
            return symbol + " "
        }
        val builder = StringBuilder()
        children.map { n -> n.yieldOperation() }.forEach { n -> builder.append(n) }
        return builder.toString()
    }

    override fun toString(): String {
        val builder = StringBuilder()

        builder.append("(")
        builder.append(symbol)

        if (isNode) {
            for (n in children) {
                builder.append(n.toString())
            }
        }

        builder.append(")")
        return builder.toString()
    }

    val isTraceTree: Boolean
        get() {
            if (this.isLeaf) return symbol.startsWith("*")
            return children.all { n -> n.isTraceTree }
        }

    fun removeTraceTrees() {
        children.filter { n -> !n.isTraceTree }.forEach { n -> n.removeTraceTrees() }
        val toRemove = LinkedList<Node>()
        children.filter { n -> n.isTraceTree }.toCollection(toRemove)
        toRemove.forEach { children.remove(it) }
    }

    fun deviatesToTerminal(): Boolean {
        return countOfChildren == 1 && getChild(0).isLeaf
    }

    private fun deviatesToNonterminal(): Boolean {
        return !deviatesToTerminal() && isNode
    }

    fun writeToLexicon(counter: WordUsageCounter) {
        children.forEach { n ->
            if (n.isLeaf) {
                counter.addWord(n.symbol, this.symbol)
                return
            }
            n.writeToLexicon(counter)
        }
    }

    companion object {
        val SEPARATOR_ARROW = " ->"
        val SEPARATOR_EMPTY = ""
    }

}
