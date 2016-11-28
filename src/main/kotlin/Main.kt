import model.Node
import model.RuleUsageCounter
import model.WordUsageCounter
import parser.TreeParser

object Main {

    private var treeCounter = 1

    @JvmStatic fun main(args: Array<String>) {
        assert(args.size == 1) { "Datei muss als einziges Argument gegeben sein" }

        val parser = TreeParser()
        val trees = parser.parseTreeFile(args[0])
        val treesWithoutTrace = parser.parseTreeFile(args[0].replace("tree", "noTrace"))

        println("\nAufgabe 1:")
        trees.forEach { printHeights(it) }

        println("\nAufgabe 2:")
        trees.forEach { printTreeWithProductions(it) }

        println("\nAufgabe 3:")
        trees.forEach { printTreesWithoutTraceTrees(it) }

        println("\nAufgabe 4:")
        printProductionsForWholeFile(treesWithoutTrace)

        println("\nAufgabe 5:")
        printLexicon(treesWithoutTrace)
    }

    private fun printHeights(node: Node) {
        println(node.height)
    }

    private fun printTreeWithProductions(node: Node) {
        println("\nProduktionen von Baum Nr. " + treeCounter++)
        node.productionsForWholeTree.forEach { println(it) }
    }

    private fun printTreesWithoutTraceTrees(node: Node) {
        node.removeTraceTrees()
        println("( $node )")
    }

    private fun printProductionsForWholeFile(trees: MutableList<Node>) {
        val counter = RuleUsageCounter(trees)
        counter.asStringList().forEach { it -> println(it) }
    }

    private fun printLexicon(trees: MutableList<Node>) {
        val counter = WordUsageCounter(trees)
        counter.asStringList().forEach { it -> println(it) }
    }
}
