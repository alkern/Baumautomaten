package model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WordUsageCounterTest {

    private var counter = WordUsageCounter()

    @Before
    fun setup() {
        counter = WordUsageCounter()
    }

    @Test
    fun testAddWord() {
        counter.addWord("Test", "TEST")
        assertEquals(1, counter.getCountFor("Test", "TEST"))
    }

    @Test
    fun testAddMultipleNonterminals() {
        counter.addWord("Test", "TEST")
        counter.addWord("Test", "TEST2")
        assertEquals(1, counter.getCountFor("Test", "TEST"))
        assertEquals(1, counter.getCountFor("Test", "TEST2"))
    }

    @Test
    fun testAddWordTwoTimes() {
        counter.addWord("Test", "TEST")
        counter.addWord("Test", "TEST")
        assertEquals(2, counter.getCountFor("Test", "TEST"))
    }

    @Test
    fun testCountForNoWordIsZero() {
        assertEquals(0, counter.getCountFor("Test", "TEST"))
    }

    @Test
    fun testGetLexiconAsStrings() {
        counter.addWord("Test", "TEST")
        counter.addWord("Test", "TEST")
        assertEquals(1, counter.getLexiconAsStrings().size)
        assertEquals("Test\tTEST 2", counter.getLexiconAsStrings()[0])
    }

    @Test
    fun testGetLexiconAsStringsWithTwoRules() {
        counter.addWord("Test", "TEST")
        counter.addWord("Test", "TEST")
        counter.addWord("Word", "NONTERMINAL")
        assertEquals(2, counter.getLexiconAsStrings().size)
        assertEquals("Test\tTEST 2", counter.getLexiconAsStrings()[0])
        assertEquals("Word\tNONTERMINAL 1", counter.getLexiconAsStrings()[1])
    }

    @Test
    fun testGetLexiconAsStringsWithTwoNonterminals() {
        counter.addWord("Test", "TEST1")
        counter.addWord("Test", "TEST2")
        assertEquals(1, counter.getLexiconAsStrings().size)
        assertEquals("Test\tTEST1 1 TEST2 1", counter.getLexiconAsStrings()[0])
    }
}