package parser;

import model.Node;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RecursiveLineParserTest {

    private RecursiveLineParser parser;
    private Tokenizer scanner;

    @Before
    public void init() {
        this.parser = new RecursiveLineParser();
        this.scanner = new Tokenizer();
    }

    @Test
    public void testExceptionForInvalidInput() {
        try {
            Node tree = parser.parseLineToTree(scanner.scan("Invalid)"));
            fail();
        } catch (RuntimeException e) {
            assertEquals("Input has to start with (", e.getMessage());
        }
    }

    @Test
    public void testOneLeaf() {
        Node tree = parser.parseLineToTree(scanner.scan("(For)"));
        assertEquals("For", tree.getSymbol());
    }

    @Test
    public void testOneNodeWithOneLeaf() {
        Node tree = parser.parseLineToTree(scanner.scan("(IN For)"));
        assertEquals("IN", tree.getSymbol());
        assertEquals(1, tree.getCountOfChildren());
        assertEquals("For", tree.getChild(0).getSymbol());
    }

    @Test
    public void testNodeWithTwoChildren() {
        List<String> tokens = scanner.scan("(S (IN For) (NNS years))");
        Node tree = parser.parseLineToTree(tokens);
        assertEquals("S", tree.getSymbol());
        assertEquals(2, tree.getCountOfChildren());
        assertEquals("IN", tree.getChild(0).getSymbol());
        assertEquals("For", tree.getChild(0, 0).getSymbol());
        assertEquals("NNS", tree.getChild(1).getSymbol());
        assertEquals("years", tree.getChild(1, 0).getSymbol());
    }

    @Test
    public void testTreeWithHeightThree() {
        Node tree = parser.parseLineToTree(scanner.scan("(A (B C))"));
        assertEquals("A", tree.getSymbol());
        assertEquals(1, tree.getCountOfChildren());
        assertEquals("B", tree.getChild(0).getSymbol());
        assertEquals("C", tree.getChild(0, 0).getSymbol());
    }

    @Test
    public void testRealExample() {
        Node tree = parser.parseLineToTree(scanner.scan("(S (PP-TMP (IN For) (NP (CD six) (NNS years))) (, ,) (NP-SBJ (NNP T.) " +
                "(NNP Marshall) (NNP Hahn) (NNP Jr.)) (VP (VBZ has) (VP (VBN made) (NP (JJ corporate) " +
                "(NNS acquisitions)) (PP-MNR (IN in) (NP (NP (DT the) (NML (NNP George) (NNP Bush)) (NN mode)) " +
                "(: :) (ADJP (JJ kind) (CC and) (JJ gentle)))))) (. .))"));
        assertEquals(5, tree.getCountOfChildren());
    }

}