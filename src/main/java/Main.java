import model.Node;
import model.RuleUsageCounter;
import model.WordUsageCounter;
import parser.TreeParser;

import java.util.List;

public class Main {

    private static int counter = 1;

    public static void main(String[] args) {
        assert(args.length == 1) : "Datei muss als einziges Argument gegeben sein";

        TreeParser parser = new TreeParser();
        List<Node> trees = parser.parseTreeFile(args[0]);
        List<Node> treesWithoutTrace = parser.parseTreeFile(args[0].replace("tree", "noTrace"));

        System.out.println("\nAufgabe 1:");
        trees.forEach(Main::printHeights);

        System.out.println("\nAufgabe 2:");
        trees.forEach(Main::printTreeWithProductions);

        System.out.println("\nAufgabe 3:");
        trees.forEach(Main::printTreesWithoutTraceTrees);

        System.out.println("\nAufgabe 4:");
        printProductionsForWholeFile(treesWithoutTrace);

        System.out.println("\nAufgabe 5:");
        printLexicon(treesWithoutTrace);
    }

    private static void printHeights(Node node) {
        System.out.println(node.getHeight());
    }

    private static void printTreeWithProductions(Node node) {
        System.out.println("\nProduktionen von Baum Nr. " + counter++);
        node.getProductionsForWholeTree().forEach(System.out::print);
    }

    private static void printTreesWithoutTraceTrees(Node node) {
        node.removeTraceTrees();
        System.out.println("( " + node + " )");
    }

    private static void printProductionsForWholeFile(List<Node> trees) {
        RuleUsageCounter counter = new RuleUsageCounter(trees);
        counter.print();
    }

    private static void printLexicon(List<Node> treesWithoutTrace) {
        WordUsageCounter counter = new WordUsageCounter(treesWithoutTrace);
        counter.print();
    }

}
