package model;

import org.junit.Test;

import java.util.Collection;

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

    @Test
    public void testGetProductionsWithChildren() {
        Node n = new Node("Test");
        n.addChild(new Node("C1"));
        n.addChild(new Node("C2"));
        assertEquals("Test -> C1 C2\n", n.getProductions(Node.SEPARATOR_ARROW));
    }

    @Test
    public void testGetProductionsWithoutChildren() {
        Node n = new Node("Test");
        assertEquals("Test ->\n", n.getProductions(Node.SEPARATOR_ARROW));
    }

    @Test
    public void testGetProductionsWithGrandchildren() {
        Node n = new Node("Test");
        Node c1 = new Node("C1");
        n.addChild(c1);
        c1.addChild(new Node("GC"));
        n.addChild(new Node("C2"));
        assertEquals("Test -> C1 C2\n", n.getProductions(Node.SEPARATOR_ARROW));
        assertEquals(2, n.getProductionsForWholeTree().size());
    }

    @Test
    public void testToStringForLeaf() {
        Node n = new Node("Test");
        assertEquals("(Test)", n.toString());
    }

    @Test
    public void testToStringForNode() {
        Node n = new Node("Test");
        n.addChild(new Node("Child"));
        assertEquals("(Test(Child))", n.toString());
    }

    @Test
    public void testToStringForNodeWithTwoChildren() {
        Node n = new Node("Test");
        n.addChild(new Node("Child"));
        n.addChild(new Node("Child"));
        assertEquals("(Test(Child)(Child))", n.toString());
    }

    @Test
    public void testToStringForNodeWithGrandchildren() {
        Node n = new Node("Test");
        Node c = new Node("Child");
        n.addChild(c);
        c.addChild(new Node("Grandchild"));
        assertEquals("(Test(Child(Grandchild)))", n.toString());
    }

    @Test
    public void testYield() {
        Node root = new Node("S");

        Node c1 = new Node("C1");
        Node gc1 = new Node("GC");
        c1.addChild(gc1);
        root.addChild(c1);

        Node c2 = new Node("C2");
        Node gc2 = new Node("GC2");
        Node ggc1 = new Node("GGC1");
        gc2.addChild(ggc1);

        Node gc3 = new Node("GC3");
        Node ggc2 = new Node("GGC2");
        gc3.addChild(ggc2);

        c2.addChild(gc2);
        c2.addChild(gc3);

        root.addChild(c2);

        assertEquals("GC GGC1 GGC2 ", root.yield());
    }

    @Test
    public void testGetProductionsWithDoublicate() {
        Node root = new Node("Root");
        Node root2 = new Node("Root");
        root.addChild(new Node("Test"));
        root.addChild(root2);

        root2.addChild(new Node("Test"));
        root2.addChild(new Node("Leaf"));

        Collection<String> productions = root.getProductionsWithDuplicates();
        assertEquals(2, productions.size());
    }

    @Test
    public void testTerminalNodeSuccessful() {
        Node root = new Node("Root");
        root.addChild(new Node("terminal"));
        assertTrue(root.deviatesToTerminal());
    }

    @Test
    public void testNonterminalNodeSuccessful() {
        Node root = new Node("Root");
        Node child = new Node("Child");
        child.addChild(new Node("terminal"));
        root.addChild(child);

        assertFalse(root.deviatesToTerminal());
        assertTrue(child.deviatesToTerminal());
    }

    @Test
    public void testWriteToDirectory() {
        Node root = new Node("ROOT");
        Node child = new Node("CHILD");
        child.addChild(new Node("terminal"));
        root.addChild(child);

        WordUsageCounter counter = new WordUsageCounter();
        root.writeToLexicon(counter);
        assertEquals(1, counter.getCountFor("terminal", "CHILD"));
        assertEquals(0, counter.getCountFor("terminal", "Child"));
        assertEquals(0, counter.getCountFor("terminal", "ROOT"));
    }

}
