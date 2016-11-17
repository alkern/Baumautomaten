package model;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public int getCountOfChildren() {
        return children.size();
    }

    boolean isLeaf() {
        return getCountOfChildren() == 0;
    }

    boolean isNode() {
        return !isLeaf();
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

    String getProductions() {
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

    public Set<String> getProductionsForWholeTree() {
        Set<String> productions = new LinkedHashSet<>();
        return (Set<String>) fillProductions(productions, Node::isNode);
    }

    public List<String> getProductionsWithDuplicates() {
        //TODO nur Ableitungen zu Nichtterminalen beachten für Aufgabe 4
        List<String> productions = new LinkedList<>();
        return (List<String>) fillProductions(productions, Node::deviatesToNonterminal);
    }

    @NotNull
    private Collection<String> fillProductions(Collection<String> productions, Predicate<Node> filter) {
        productions.add(getProductions());
        children.stream().filter(filter)
                .forEach(node -> productions.addAll(node.getProductionsForWholeTree()));
        return productions;
    }

    String yield() {
        if (isLeaf()) {
            return getSymbol() + " ";
        }
        StringBuilder builder = new StringBuilder();
        children.stream().map(Node::yield).forEach(builder::append);
        return builder.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("(");
        builder.append(getSymbol());

        if (isNode()) {
            for (Node n : children) {
                builder.append(n.toString());
            }
        }

        builder.append(")");
        return builder.toString();
    }

    public boolean isTraceTree() {
        if (this.isLeaf()) return getSymbol().startsWith("*");
        return children.stream().allMatch(Node::isTraceTree);
    }

    public void removeTraceTrees() {
        children.stream().filter(n -> !n.isTraceTree()).forEach(Node::removeTraceTrees);
        List<Node> toRemove = children.stream().filter(Node::isTraceTree).collect(Collectors.toList());
        toRemove.forEach(children::remove);
    }

    public boolean deviatesToTerminal() {
        return isNode() && getCountOfChildren() == 1 && getChild(0).isLeaf();
    }

    public boolean deviatesToNonterminal() {
        return !deviatesToTerminal();
    }

}
