package parser

import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class TokenizerTest {

    private var scanner: Tokenizer? = null

    @Before
    fun init() {
        scanner = Tokenizer()
    }

    @Test
    fun testExceptionForInvalidInput() {
        try {
            scanner!!.scan("Invalid)")
            fail()
        } catch (e: RuntimeException) {
            assertEquals("Input has to start with (", e.message)
        }

    }

    @Test
    fun testOneLeaf() {
        val tokens = scanner!!.scan("(For)")
        assertEquals(3, tokens.size.toLong())
    }

    @Test
    fun testOneNodeWithOneLeaf() {
        val tokens = scanner!!.scan("(IN For)")
        assertEquals(4, tokens.size.toLong())
    }

    @Test
    fun testNodeWithTwoChildren() {
        val tokens = scanner!!.scan("(S (IN For) (NNS years))")
        assertEquals(11, tokens.size.toLong())
    }

    @Test
    fun testTreeWithHeightThree() {
        val tokens = scanner!!.scan("(A (B C))")
        assertEquals(7, tokens.size.toLong())
    }

    @Test
    fun testRealExample() {
        val tokens = scanner!!.scan("(S (PP-TMP (IN For) (NP (CD six) (NNS years))) (, ,) (NP-SBJ (NNP T.) " +
                "(NNP Marshall) (NNP Hahn) (NNP Jr.)) (VP (VBZ has) (VP (VBN made) (NP (JJ corporate) " +
                "(NNS acquisitions)) (PP-MNR (IN in) (NP (NP (DT the) (NML (NNP George) (NNP Bush)) (NN mode)) " +
                "(: :) (ADJP (JJ kind) (CC and) (JJ gentle)))))) (. .))")
        assertEquals(124, tokens.size.toLong())
    }

}