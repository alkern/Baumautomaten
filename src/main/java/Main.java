import model.Node;
import parser.TreeParser;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        assert(args.length == 1);

        TreeParser parser = new TreeParser();
        List<Node> trees = parser.parseTreeFile(args[0]);

        System.out.println("Höhen:");
        trees.forEach(Main::printHeights);
    }

    private static void printHeights(Node node) {
        System.out.println(node.getHeight());
    }

}
