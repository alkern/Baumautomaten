package model

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class NodeTest {

    @Test
    fun testIsLeafForLeaf() {
        val n = Node("Test")
        assertTrue(n.isLeaf)
    }

    @Test
    fun testIsLeafForNode() {
        val n = Node("Test")
        n.addChild(Node("Child"))
        assertFalse(n.isLeaf)
    }

    @Test
    fun testGetChildOverOneGeneration() {
        val n = Node("Test")
        n.addChild(Node("Child"))
        assertEquals("Child", n.getChild(0).symbol)
    }

    @Test
    fun testGetChildOverTwoGenerations() {
        val n = Node("Test")
        val c = Node("Child")
        n.addChild(c)
        c.addChild(Node("Grandchild"))
        assertEquals("Grandchild", n.getChild(0, 0).symbol)
    }

    @Test
    fun testGetHeightOfLeaf() {
        assertEquals(0, Node("").height.toLong())
    }

    @Test
    fun testGetHeightOfNodeWithOneChild() {
        val n = Node("")
        n.addChild(Node(""))
        assertEquals(1, n.height.toLong())
    }

    @Test
    fun testGetHeightOfNodeWithTwoChild() {
        val n = Node("")
        n.addChild(Node(""))
        n.addChild(Node(""))
        assertEquals(1, n.height.toLong())
    }

    @Test
    fun testGetHeightOfNodeWithGrandchild() {
        val n = Node("Test")
        val c = Node("Child")
        n.addChild(c)
        c.addChild(Node("Grandchild"))
        assertEquals(2, n.height.toLong())
    }

    @Test
    fun testGetProductionsWithChildren() {
        val n = Node("Test")
        n.addChild(Node("C1"))
        n.addChild(Node("C2"))
        assertEquals("Test -> C1 C2", n.getProductions(Node.SEPARATOR_ARROW))
    }

    @Test
    fun testGetProductionsWithoutChildren() {
        val n = Node("Test")
        assertEquals("Test ->", n.getProductions(Node.SEPARATOR_ARROW))
    }

    @Test
    fun testGetProductionsWithGrandchildren() {
        val n = Node("Test")
        val c1 = Node("C1")
        n.addChild(c1)
        c1.addChild(Node("GC"))
        n.addChild(Node("C2"))
        assertEquals("Test -> C1 C2", n.getProductions(Node.SEPARATOR_ARROW))
        assertEquals(2, n.productionsForWholeTree.size.toLong())
    }

    @Test
    fun testToStringForLeaf() {
        val n = Node("Test")
        assertEquals("(Test)", n.toString())
    }

    @Test
    fun testToStringForNode() {
        val n = Node("Test")
        n.addChild(Node("Child"))
        assertEquals("(Test(Child))", n.toString())
    }

    @Test
    fun testToStringForNodeWithTwoChildren() {
        val n = Node("Test")
        n.addChild(Node("Child"))
        n.addChild(Node("Child"))
        assertEquals("(Test(Child)(Child))", n.toString())
    }

    @Test
    fun testToStringForNodeWithGrandchildren() {
        val n = Node("Test")
        val c = Node("Child")
        n.addChild(c)
        c.addChild(Node("Grandchild"))
        assertEquals("(Test(Child(Grandchild)))", n.toString())
    }

    @Test
    fun testYield() {
        val root = Node("S")

        val c1 = Node("C1")
        val gc1 = Node("GC")
        c1.addChild(gc1)
        root.addChild(c1)

        val c2 = Node("C2")
        val gc2 = Node("GC2")
        val ggc1 = Node("GGC1")
        gc2.addChild(ggc1)

        val gc3 = Node("GC3")
        val ggc2 = Node("GGC2")
        gc3.addChild(ggc2)

        c2.addChild(gc2)
        c2.addChild(gc3)

        root.addChild(c2)

        assertEquals("GC GGC1 GGC2 ", root.yieldOperation())
    }

    @Test
    fun testGetProductionsWithDuplicate() {
        val root = Node("Root")
        val root2 = Node("Root")
        root.addChild(Node("Test"))
        root.addChild(root2)

        root2.addChild(Node("Test"))
        root2.addChild(Node("Leaf"))

        val productions = root.productionsWithDuplicates
        assertEquals(2, productions.size.toLong())
    }

    @Test
    fun testTerminalNodeSuccessful() {
        val root = Node("Root")
        root.addChild(Node("terminal"))
        assertTrue(root.deviatesToTerminal())
    }

    @Test
    fun testNonterminalNodeSuccessful() {
        val root = Node("Root")
        val child = Node("Child")
        child.addChild(Node("terminal"))
        root.addChild(child)

        assertFalse(root.deviatesToTerminal())
        assertTrue(child.deviatesToTerminal())
    }

    @Test
    fun testWriteToDirectory() {
        val root = Node("ROOT")
        val child = Node("CHILD")
        child.addChild(Node("terminal"))
        root.addChild(child)

        val counter = WordUsageCounter(LinkedList<Node>())
        root.writeToLexicon(counter)
        assertEquals(1, counter.getCountFor("terminal", "CHILD").toLong())
        assertEquals(0, counter.getCountFor("terminal", "Child").toLong())
        assertEquals(0, counter.getCountFor("terminal", "ROOT").toLong())
    }

}
