package model

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class RootSymbolCounterTest {

    @Test
    fun testAddingMultipleRoots() {
        val counter = RootSymbolCounter(LinkedList<Node>())

        counter.addRootSymbol("Root1")
        counter.addRootSymbol("Root2")
        counter.addRootSymbol("Root3")
        counter.addRootSymbol("Root1")
        counter.addRootSymbol("Root2")

        assertEquals(2, counter.getCounterFor("Root1"))
        assertEquals(2, counter.getCounterFor("Root2"))
        assertEquals(1, counter.getCounterFor("Root3"))
        assertEquals(5, counter.counter)
    }

    @Test
    fun testUnknownSymbolReturnsZero() {
        val counter = RootSymbolCounter(LinkedList<Node>())
        assertEquals(0, counter.getCounterFor("Root1"))
        assertEquals(0, counter.getCounterFor("Root2"))
        assertEquals(0, counter.getCounterFor("Root3"))
        assertEquals(0, counter.counter)
    }

    @Test
    fun testEvaluationOfProbabilities() {
        val counter = RootSymbolCounter(LinkedList<Node>())

        counter.addRootSymbol("Root1")
        counter.addRootSymbol("Root2")
        counter.addRootSymbol("Root3")
        counter.addRootSymbol("Root1")
        counter.addRootSymbol("Root2")

        assertEquals(0.4, counter.evaluateProbabilityOf("Root1"), 0.05)
        assertEquals(0.4, counter.evaluateProbabilityOf("Root2"), 0.05)
        assertEquals(0.2, counter.evaluateProbabilityOf("Root3"), 0.05)
        assertEquals(0.0, counter.evaluateProbabilityOf("Root4"), 0.0)
    }

}