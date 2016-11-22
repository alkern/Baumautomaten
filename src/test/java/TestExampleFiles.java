import model.Node;
import model.RuleUsageCounter;
import org.junit.Test;
import parser.TestDataParser;
import parser.TreeParser;

import java.util.List;

import static org.junit.Assert.fail;

public class TestExampleFiles {

    @Test
    public void compareExampleFilesForTrace() {
        TreeParser inputParser = new TreeParser();
        List<Node> input = inputParser.parseTreeFile("wsj_0100.tree");
        input.forEach(Node::removeTraceTrees);

        TreeParser testDataParser = new TreeParser();
        List<Node> testData = testDataParser.parseTreeFile("wsj_0100.noTrace");

        for (int x = 0; x < input.size(); x++) {
            if (!input.get(x).toString().equals(testData.get(x).toString())) {
                fail();
            }
        }
    }

    @Test
    public void compareExampleFilesForGrammar() {
        TreeParser inputParser = new TreeParser();
        List<Node> input = inputParser.parseTreeFile("wsj_0100.noTrace");

        TestDataParser testParser = new TestDataParser();
        List<String> grammar = testParser.readGrammar();

        RuleUsageCounter counter = new RuleUsageCounter(input);
        List<String> inputGrammar = counter.asStringList();

        inputGrammar.forEach(foundRule -> {
            if (!grammar.contains(foundRule)) {
                fail();
            }
        });
    }
}
