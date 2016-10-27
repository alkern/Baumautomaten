import model.Node;
import parser.TreeParser;

import java.util.List;

public class Main {

    private static int counter = 1;

    public static void main(String[] args) {
        assert(args.length == 1);

        TreeParser parser = new TreeParser();
        List<Node> trees = parser.parseTreeFile(args[0]);

        System.out.println("Höhen:");
        trees.forEach(Main::printHeights);

        trees.forEach(Main::printTreeWithProductions);
    }

    private static void printTreeWithProductions(Node node) {
        System.out.println("\nProduktionen von Baum Nr. " + counter++);
        node.getProductionsForWholeTree().forEach(System.out::print);
    }

    private static void printHeights(Node node) {
        System.out.println(node.getHeight());
    }

}
