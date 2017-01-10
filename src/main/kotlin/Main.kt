import model.*
import parser.TreeParser
import java.util.*

object Main {

    @JvmStatic fun main(args: Array<String>) {
        assert(args.size == 1) { "Datei muss als einziges Argument gegeben sein" }

        val parser = TreeParser()
        val trees = parser.parseTreeFile(args[0])
                ?: throw RuntimeException("File ${args[0]} konnte nicht geparst werden")
        val treesWithoutTrace = parser.parseTreeFile(args[0].replace("tree", "noTrace"))
                ?: createTraceFreeTreesFrom(trees)

        println("\nAufgabe 1:")
        printHeights(trees)

        println("\nAufgabe 2:")
        printTreeWithProductions(trees)

        println("\nAufgabe 3:")
        printTreesWithoutTraceTrees(trees)

        println("\nAufgabe 4:")
        printProductionsForWholeFile(treesWithoutTrace)

        println("\nAufgabe 5:")
        printLexicon(treesWithoutTrace)

        println("\nAufgabe 6:")
        printProbabilities(treesWithoutTrace)

        println("\nAufgabe 7:")
        printRegularProductions(treesWithoutTrace)
    }

    private fun createTraceFreeTreesFrom(trees: MutableList<Node>?): MutableList<Node> {
        val treesWithoutTrace = LinkedList<Node>(trees)
        treesWithoutTrace.forEach { it.removeTraceTrees() }
        return treesWithoutTrace
    }

    private fun printHeights(trees: MutableList<Node>) {
        trees.forEach { println(it.height) }
    }

    private fun printTreeWithProductions(trees: MutableList<Node>) {
        var treeCounter = 1
        trees.forEach { tree ->
            println("\nProduktionen von Baum Nr. ${treeCounter++}")
            tree.productionsForWholeTree.forEach { println(it) }
        }
    }

    private fun printTreesWithoutTraceTrees(trees: MutableList<Node>) {
        trees.forEach {
            it.removeTraceTrees()
            println("( $it )")
        }
    }

    private fun printProductionsForWholeFile(trees: MutableList<Node>) {
        val counter = RuleUsageCounter(trees)
        counter.asStringList().forEach { println(it) }
    }

    private fun printLexicon(trees: MutableList<Node>) {
        val counter = WordUsageCounter(trees)
        counter.asStringList().forEach { println(it) }
    }

    private fun printProbabilities(trees: MutableList<Node>) {
        val counter = RootSymbolCounter(trees)
        counter.print()
    }

    private fun printRegularProductions(trees: MutableList<Node>) {
        val regularRules = RegularRuleCounter(trees)
        regularRules.print()
    }
}
