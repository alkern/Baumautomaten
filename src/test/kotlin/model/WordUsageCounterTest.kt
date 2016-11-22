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
}