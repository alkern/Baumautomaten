package parser;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TokenizerTest {

    private Tokenizer scanner;
    
    @Before
    public void init() {
        scanner = new Tokenizer();
    }

    @Test
    public void testExceptionForInvalidInput() {
        try {
            List<String> tokens = scanner.scan("Invalid)");
            fail();
        } catch (RuntimeException e) {
            assertEquals("Input has to start with (", e.getMessage());
        }
    }

    @Test
    public void testOneLeaf() {
        List<String> tokens = scanner.scan("(For)");
        assertEquals(3, tokens.size());
    }

    @Test
    public void testOneNodeWithOneLeaf() {
        List<String> tokens = scanner.scan("(IN For)");
        assertEquals(4, tokens.size());
    }

    @Test
    public void testNodeWithTwoChildren() {
        List<String> tokens = scanner.scan("(S (IN For) (NNS years))");
        assertEquals(11, tokens.size());
    }

    @Test
    public void testTreeWithHeightThree() {
        List<String> tokens = scanner.scan("(A (B C))");
        assertEquals(7, tokens.size());
    }

    @Test
    public void testRealExample() {
        List<String> tokens = scanner.scan("(S (PP-TMP (IN For) (NP (CD six) (NNS years))) (, ,) (NP-SBJ (NNP T.) " +
                "(NNP Marshall) (NNP Hahn) (NNP Jr.)) (VP (VBZ has) (VP (VBN made) (NP (JJ corporate) " +
                "(NNS acquisitions)) (PP-MNR (IN in) (NP (NP (DT the) (NML (NNP George) (NNP Bush)) (NN mode)) " +
                "(: :) (ADJP (JJ kind) (CC and) (JJ gentle)))))) (. .))");
        assertEquals(124, tokens.size());
    }

}