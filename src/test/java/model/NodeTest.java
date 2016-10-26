package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class NodeTest {

    @Test
    public void testIsLeafForLeaf() {
        Node n = new Node("Test");
        assertTrue(n.isLeaf());
    }

    @Test
    public void testIsLeafForNode() {
        Node n = new Node("Test");
        n.addChild(new Node("Child"));
        assertFalse(n.isLeaf());
    }

    @Test
    public void testGetChildOverOneGeneration() {
        Node n = new Node("Test");
        n.addChild(new Node("Child"));
        assertEquals("Child", n.getChild(0).getSymbol());
    }

    @Test
    public void testGetChildOverTwoGenerations() {
        Node n = new Node("Test");
        Node c = new Node("Child");
        n.addChild(c);
        c.addChild(new Node("Grandchild"));
        assertEquals("Grandchild", n.getChild(0, 0).getSymbol());
    }

    @Test
    public void testGetHeightOfLeaf() {
        assertEquals(0, new Node("").getHeight());
    }

    @Test
    public void testGetHeightOfNodeWithOneChild() {
        Node n = new Node("");
        n.addChild(new Node(""));
        assertEquals(1, n.getHeight());
    }

    @Test
    public void testGetHeightOfNodeWithTwoChild() {
        Node n = new Node("");
        n.addChild(new Node(""));
        n.addChild(new Node(""));
        assertEquals(1, n.getHeight());
    }

    @Test
    public void testGetHeightOfNodeWithGrandchild() {
        Node n = new Node("Test");
        Node c = new Node("Child");
        n.addChild(c);
        c.addChild(new Node("Grandchild"));
        assertEquals(2, n.getHeight());
    }
}