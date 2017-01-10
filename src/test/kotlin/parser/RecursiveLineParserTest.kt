package parser

import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class RecursiveLineParserTest {

    private var parser: RecursiveLineParser? = null
    private var scanner: Tokenizer? = null

    @Before
    fun init() {
        this.parser = RecursiveLineParser()
        this.scanner = Tokenizer()
    }

    @Test
    fun testExceptionForInvalidInput() {
        try {
            parser!!.parseLinesToTree(scanner!!.scan("Invalid)"))
            fail()
        } catch (e: RuntimeException) {
            assertEquals("Input has to start with (", e.message)
        }

    }

    @Test
    fun testOneLeaf() {
        val tree = parser!!.parseLinesToTree(scanner!!.scan("(For)"))
        assertEquals("For", tree.symbol)
    }

    @Test
    fun testOneNodeWithOneLeaf() {
        val tree = parser!!.parseLinesToTree(scanner!!.scan("(IN For)"))
        assertEquals("IN", tree.symbol)
        assertEquals(1, tree.countOfChildren.toLong())
        assertEquals("For", tree.getChild(0).symbol)
    }

    @Test
    fun testNodeWithTwoChildren() {
        val tokens = scanner!!.scan("(S (IN For) (NNS years))")
        val tree = parser!!.parseLinesToTree(tokens)
        assertEquals("S", tree.symbol)
        assertEquals(2, tree.countOfChildren.toLong())
        assertEquals("IN", tree.getChild(0).symbol)
        assertEquals("For", tree.getChild(0, 0).symbol)
        assertEquals("NNS", tree.getChild(1).symbol)
        assertEquals("years", tree.getChild(1, 0).symbol)
    }

    @Test
    fun testTreeWithHeightThree() {
        val tree = parser!!.parseLinesToTree(scanner!!.scan("(A (B C))"))
        assertEquals("A", tree.symbol)
        assertEquals(1, tree.countOfChildren.toLong())
        assertEquals("B", tree.getChild(0).symbol)
        assertEquals("C", tree.getChild(0, 0).symbol)
    }

    @Test
    fun testRealExample() {
        val tree = parser!!.parseLinesToTree(scanner!!.scan("(S (PP-TMP (IN For) (NP (CD six) (NNS years))) (, ,) (NP-SBJ (NNP T.) " +
                "(NNP Marshall) (NNP Hahn) (NNP Jr.)) (VP (VBZ has) (VP (VBN made) (NP (JJ corporate) " +
                "(NNS acquisitions)) (PP-MNR (IN in) (NP (NP (DT the) (NML (NNP George) (NNP Bush)) (NN mode)) " +
                "(: :) (ADJP (JJ kind) (CC and) (JJ gentle)))))) (. .))"))
        assertEquals(5, tree.countOfChildren.toLong())
    }

}