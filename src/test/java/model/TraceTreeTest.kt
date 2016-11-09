package model

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TraceTreeTest {

    @Test
    fun testTraceLeaf() {
        val trace = Node("*Test")
        assertTrue(trace.isTraceTree)
    }

    @Test
    fun testNormalLeaf() {
        val trace = Node("Test")
        assertFalse(trace.isTraceTree)
    }

    @Test
    fun testTraceNode() {
        val node = Node("Test")
        val trace1 = Node("*1")
        val trace2 = Node("*2")

        node.addChild(trace1)
        node.addChild(trace2)

        assertTrue(node.isTraceTree)
    }

    @Test
    fun testNormalNode() {
        val node = Node("Test")
        val trace1 = Node("*1")
        val trace2 = Node("2")

        node.addChild(trace1)
        node.addChild(trace2)

        assertFalse(node.isTraceTree)
    }

    @Test
    fun testThreeGenerationsThatAreTrace() {
        val parent = Node("Parent")
        val leftChild = Node("Left")
        val rightChild = Node("*Right")
        val leftGrandchild = Node("*Test")
        val rightGrandchild = Node("*Test")

        leftChild.addChild(leftGrandchild)
        leftChild.addChild(rightGrandchild)
        parent.addChild(leftChild)
        parent.addChild(rightChild)

        assertTrue(parent.isTraceTree)
    }

    @Test
    fun testThreeGenerationsThatAreNotTrace() {
        val parent = Node("Parent")
        val leftChild = Node("Left")
        val rightChild = Node("Right")
        val leftGrandchild = Node("*Test")
        val rightGrandchild = Node("*Test")

        leftChild.addChild(leftGrandchild)
        leftChild.addChild(rightGrandchild)
        parent.addChild(leftChild)
        parent.addChild(rightChild)

        assertFalse(parent.isTraceTree)
    }
}
