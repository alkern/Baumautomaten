import model.RuleUsageCounter
import model.WordUsageCounter
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import parser.TestDataParser
import parser.TreeParser

class TestExampleFiles {

    @Test
    fun compareExampleTraceFile() {
        val inputParser = TreeParser()
        val input = inputParser.parseTreeFile("wsj_0100.tree")
        input.forEach { it.removeTraceTrees() }

        val testDataParser = TreeParser()
        val testData = testDataParser.parseTreeFile("wsj_0100.noTrace")

        for (x in input.indices) {
            if (input[x].toString() != testData[x].toString()) {
                fail()
            }
        }
    }

    @Test
    fun compareExampleGrammarFile() {
        val inputParser = TreeParser()
        val input = inputParser.parseTreeFile("wsj_0100.noTrace")

        val testParser = TestDataParser()
        val grammar = testParser.readGrammar()

        val counter = RuleUsageCounter(input)
        val inputGrammar = counter.asStringList()

        inputGrammar.forEach { foundRule ->
            if (!grammar.contains(foundRule)) {
                fail()
            }
        }
    }

    @Test
    fun compareExampleLexiconFile() {
        val inputParser = TreeParser()
        val input = inputParser.parseTreeFile("wsj_0100.noTrace")

        val testParser = TestDataParser()
        val grammar = testParser.readLexicon()

        val counter = WordUsageCounter(input)
        val inputGrammar = counter.asStringList()

        assertEquals(grammar.size.toLong(), inputGrammar.size.toLong())
    }

}
