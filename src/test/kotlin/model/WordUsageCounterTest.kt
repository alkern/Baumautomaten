package model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class WordUsageCounterTest {

    private var counter = WordUsageCounter(LinkedList<Node>())

    @Before
    fun setup() {
        counter = WordUsageCounter(LinkedList<Node>())
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
        assertEquals(1, counter.asStringList().size)
        assertEquals("Test\tTEST 2", counter.asStringList()[0])
    }

    @Test
    fun testGetLexiconAsStringsWithTwoRules() {
        counter.addWord("Test", "TEST")
        counter.addWord("Test", "TEST")
        counter.addWord("Word", "NONTERMINAL")
        assertEquals(2, counter.asStringList().size)
        assertEquals("Test\tTEST 2", counter.asStringList()[0])
        assertEquals("Word\tNONTERMINAL 1", counter.asStringList()[1])
    }

    @Test
    fun testGetLexiconAsStringsWithTwoNonterminals() {
        counter.addWord("Test", "TEST1")
        counter.addWord("Test", "TEST2")
        assertEquals(1, counter.asStringList().size)
        assertEquals("Test\tTEST1 1 TEST2 1", counter.asStringList()[0])
    }
}