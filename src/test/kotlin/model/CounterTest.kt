package model

import org.junit.Assert.assertEquals
import org.junit.Test

class CounterTest {

    @Test
    fun testAddOneValue() {
        val counter = Counter<String>()
        counter.addValue("Test")
        assertEquals(1, counter.getCounterFor("Test"))
    }

    @Test
    fun testAddOneValueTwice() {
        val counter = Counter<String>()
        counter.addValue("Test")
        counter.addValue("Test")
        assertEquals(2, counter.getCounterFor("Test"))
    }

    @Test
    fun testZeroForUnaddedValue() {
        val counter = Counter<String>()
        counter.addValue("Test")
        assertEquals(0, counter.getCounterFor("a"))
    }

    @Test
    fun testSum() {
        val counter = Counter<String>()
        counter.addValue("Test")
        assertEquals(1, counter.sum)
        counter.addValue("Test")
        assertEquals(2, counter.sum)
        counter.addValue("a")
        assertEquals(3, counter.sum)
    }

    @Test
    fun testValues() {
        val counter = Counter<String>()
        counter.addValue("Test")
        assertEquals(1, counter.values.size)
        counter.addValue("Test")
        assertEquals(1, counter.values.size)
        counter.addValue("a")
        assertEquals(2, counter.values.size)
    }
}