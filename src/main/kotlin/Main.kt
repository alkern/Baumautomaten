import model.Node
import model.RootSymbolCounter
import model.RuleUsageCounter
import model.WordUsageCounter
import parser.TreeParser
import java.util.*

object Main {


    @JvmStatic fun main(args: Array<String>) {
        assert(args.size == 1) { "Datei muss als einziges Argument gegeben sein" }

        val parser = TreeParser()
        val trees = parser.parseTreeFile(args[0])
        var treesWithoutTrace = parser.parseTreeFile(args[0].replace("tree", "noTrace"))

        if (treesWithoutTrace.size == 0) {
            treesWithoutTrace = LinkedList<Node>(trees)
            treesWithoutTrace.forEach { tree -> tree.removeTraceTrees() }
        }

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
    }

    private fun printHeights(trees: MutableList<Node>) {
        trees.forEach { println(it.height) }
    }

    private fun printTreeWithProductions(trees: MutableList<Node>) {
        var treeCounter = 1
        trees.forEach { tree ->
            run {
                println("\nProduktionen von Baum Nr. " + treeCounter++)
                tree.productionsForWholeTree.forEach { println(it) }
            }
        }
    }

    private fun printTreesWithoutTraceTrees(trees: MutableList<Node>) {
        trees.forEach {
            run {
                it.removeTraceTrees()
                println("( $it )")
            }
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
}
