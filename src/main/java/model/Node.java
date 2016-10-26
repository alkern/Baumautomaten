package model;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private final List<Node> children;
    private final String nodeSymbol;

    public Node(String symbol) {
        this.children = new ArrayList<Node>();
        this.nodeSymbol = symbol;
    }

    public String getSymbol() {
        return nodeSymbol;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }

    public int getCountOfChildren() {
        return children.size();
    }

    public Node getChild(int number) {
        return children.get(number);
    }

    public Node getChild(int... numbers) {
        Node result = this;
        for (int i : numbers) {
            result = result.getChild(i);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("(");
        builder.append(getSymbol());

        if (!isLeaf()) {
            for (Node n : children) {
                builder.append(n.toString());
            }
        }

        builder.append(")");
        return builder.toString();
    }
}
