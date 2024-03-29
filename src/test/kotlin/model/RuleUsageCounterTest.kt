package model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class RuleUsageCounterTest {

    var ruleUsageCounter = RuleUsageCounter(LinkedList<Node>())

    @Before
    fun setup() {
        ruleUsageCounter = RuleUsageCounter(LinkedList<Node>())
    }

    @Test
    fun testSuccessfulAddingOfRule() {
        assertEquals(0, ruleUsageCounter.getCounterFor("Test"))
        ruleUsageCounter.addRule("Test")
        assertEquals(1, ruleUsageCounter.getCounterFor("Test"))
    }

    @Test
    fun testDoubleAddingOfRule() {
        assertEquals(0, ruleUsageCounter.getCounterFor("Test"))
        ruleUsageCounter.addRule("Test")
        ruleUsageCounter.addRule("Test")
        assertEquals(2, ruleUsageCounter.getCounterFor("Test"))
    }

    @Test
    fun testAddingOfRuleList() {
        assertEquals(0, ruleUsageCounter.getCounterFor("Test"))
        val rules = LinkedList<String>()
        rules.add("Test")
        rules.add("Test")
        ruleUsageCounter.addRules(rules)
        assertEquals(2, ruleUsageCounter.getCounterFor("Test"))
    }
}