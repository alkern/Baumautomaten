package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Node {

    private final List<Node> children;
    private final String nodeSymbol;

    public Node(String symbol) {
        this.children = new ArrayList<>();
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

    public int getHeight() {
        if (isLeaf()) {
            return 0;
        }
        int max = 0;
        for (Node n : children) {
            if (max < n.getHeight()) {
                max = n.getHeight();
            }
        }
        return 1 + max;
    }

    public String getProductions() {
        StringBuilder builder = new StringBuilder();
        builder.append(getSymbol());
        builder.append(" ->");
        for (Node child : children) {
            builder.append(" ");
            builder.append(child.getSymbol());
        }
        builder.append("\n");
        return builder.toString();
    }

    public List<String> getProductionsForWholeTree() {
        List<String> productions = new LinkedList<>();

        productions.add(getProductions());
        children.stream().filter(node -> !node.isLeaf())
                .forEach(node -> productions.addAll(node.getProductionsForWholeTree()));

        return productions;
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
