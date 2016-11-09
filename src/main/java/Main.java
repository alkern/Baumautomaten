import model.Node;
import parser.TreeParser;

import java.util.List;

public class Main {

    private static int counter = 1;

    public static void main(String[] args) {
        assert(args.length == 1) : "Datei muss als einziges Argument gegeben sein";

        TreeParser parser = new TreeParser();
        List<Node> trees = parser.parseTreeFile(args[0]);

        System.out.println("\nAufgabe 1:");
        trees.forEach(Main::printHeights);

        System.out.println("\nAufgabe 2:");
        trees.forEach(Main::printTreeWithProductions);

        System.out.println("\nAufgabe 3:");
        trees.forEach(Main::printTreesWithoutTraceTrees);
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

}
