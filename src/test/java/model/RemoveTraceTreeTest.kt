package model

import org.junit.Assert
import org.junit.Test


class RemoveTraceTreeTest {

    @Test
    fun testTraceLeaf() {
        val root = Node("Root")
        val trace = Node("*Test")
        root.addChild(trace)
        root.removeTraceTrees()

        Assert.assertEquals(0, root.countOfChildren)
    }

    @Test
    fun testNormalLeaf() {
        val root = Node("Root")
        val trace = Node("Test")
        root.addChild(trace)
        root.removeTraceTrees()

        Assert.assertEquals(1, root.countOfChildren)
    }

    @Test
    fun testTraceNode() {
        val root = Node("Root")
        val node = Node("Test")
        val trace1 = Node("*1")
        val trace2 = Node("*2")

        node.addChild(trace1)
        node.addChild(trace2)
        root.addChild(node)
        root.removeTraceTrees()

        Assert.assertEquals(0, root.countOfChildren)
    }

    @Test
    fun testNormalNode() {
        val root = Node("Root")
        val node = Node("Test")
        val trace1 = Node("*1")
        val trace2 = Node("2")

        node.addChild(trace1)
        node.addChild(trace2)
        root.addChild(node)
        root.removeTraceTrees()

        Assert.assertEquals(1, root.countOfChildren)
    }

    @Test
    fun testThreeGenerationsThatAreTrace() {
        val root = Node("Root")
        val parent = Node("Parent")
        val leftChild = Node("Left")
        val rightChild = Node("*Right")
        val leftGrandchild = Node("*Test")
        val rightGrandchild = Node("*Test")

        leftChild.addChild(leftGrandchild)
        leftChild.addChild(rightGrandchild)
        parent.addChild(leftChild)
        parent.addChild(rightChild)
        root.addChild(parent)
        root.removeTraceTrees()

        Assert.assertEquals(0, root.countOfChildren)
    }

    @Test
    fun testThreeGenerationsThatAreNotTrace() {
        val root = Node("Root")
        val parent = Node("Parent")
        val leftChild = Node("Left")
        val rightChild = Node("Right")
        val leftGrandchild = Node("*Test")
        val rightGrandchild = Node("*Test")

        leftChild.addChild(leftGrandchild)
        leftChild.addChild(rightGrandchild)
        parent.addChild(leftChild)
        parent.addChild(rightChild)
        root.addChild(parent)

        Assert.assertEquals(2, parent.countOfChildren)
        root.removeTraceTrees()
        Assert.assertEquals(1, parent.countOfChildren)
    }
}