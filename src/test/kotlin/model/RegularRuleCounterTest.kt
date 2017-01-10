package model

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class RegularRuleCounterTest {

    @Test
    fun testCounting() {
        val regularRules = RegularRuleCounter(LinkedList<Node>())
        val rule1 = RegularRule("A", Node.SEPARATOR_ARROW, "A(b c")
        val rule2 = RegularRule("B", Node.SEPARATOR_ARROW, "B(b c")
        regularRules.putRule(rule1)
        regularRules.putRule(rule2)
        assertEquals(1, regularRules.getCounterFor(rule1))
        assertEquals(1, regularRules.getCounterFor(rule2))
        regularRules.putRule(rule1)
        assertEquals(2, regularRules.getCounterFor(rule1))
    }

    @Test
    fun testCounterIsZeroForUnaddedRule() {
        val regularRules = RegularRuleCounter(LinkedList<Node>())
        val rule1 = RegularRule("A", Node.SEPARATOR_ARROW, "A(b c")
        assertEquals(0, regularRules.getCounterFor(rule1))
    }

    @Test
    fun testGetSumForSymbol() {
        val regularRules = RegularRuleCounter(LinkedList<Node>())
        val rule1 = RegularRule("A", Node.SEPARATOR_ARROW, "A(b c")
        val rule2 = RegularRule("A", Node.SEPARATOR_ARROW, "A(b c d")
        regularRules.putRule(rule1)
        assertEquals(1, regularRules.getCounterSumForSymbol("A"))
        regularRules.putRule(rule1)
        assertEquals(2, regularRules.getCounterSumForSymbol("A"))
        regularRules.putRule(rule2)
        assertEquals(3, regularRules.getCounterSumForSymbol("A"))
    }

    @Test
    fun testProductionWeights() {
        val regularRules = RegularRuleCounter(LinkedList<Node>())
        val rule1 = RegularRule("A", Node.SEPARATOR_ARROW, "A(b c")
        val rule2 = RegularRule("A", Node.SEPARATOR_ARROW, "A(b c d")
        regularRules.putRule(rule1)
        assertEquals(1.0, regularRules.getProductionWeightFor(rule1), 0.0)
        regularRules.putRule(rule1)
        assertEquals(1.0, regularRules.getProductionWeightFor(rule1), 0.0)
        regularRules.putRule(rule2)
        assertEquals(0.6, regularRules.getProductionWeightFor(rule1), 0.1)
        assertEquals(0.3, regularRules.getProductionWeightFor(rule2), 0.1)
    }

    @Test
    fun testProductionWeightZeroForUnaddedRule() {
        val regularRules = RegularRuleCounter(LinkedList<Node>())
        val rule1 = RegularRule("A", Node.SEPARATOR_ARROW, "A(b c")
        assertEquals(0.0, regularRules.getProductionWeightFor(rule1), 0.0)
    }
}