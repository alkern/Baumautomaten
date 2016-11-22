package model;

import java.util.*;
import java.util.stream.Collectors;

public class Node {

    public static final String SEPARATOR_ARROW = " ->";
    public static final String SEPARATOR_EMPTY = "";

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

    private boolean isNode() {
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

    /**
     * @param separator Trennezichen zwischen linker und rechter Seite der Produktionsregel
     * @return Produktionsregel für diesen Node
     */
    String getProductions(String separator) {
        StringBuilder builder = new StringBuilder();
        builder.append(getSymbol());
        builder.append(separator);
        for (Node child : children) {
            builder.append(" ");
            builder.append(child.getSymbol());
        }
        builder.append("\n");
        return builder.toString();
    }

    public Set<String> getProductionsForWholeTree() {
        Set<String> productions = new LinkedHashSet<>();
        productions.add(getProductions(SEPARATOR_ARROW));
        children.stream().filter(Node::isNode)
                .forEach(node -> productions.addAll(node.getProductionsForWholeTree()));
        return productions;
    }

    public List<String> getProductionsWithDuplicates() {
        List<String> productions = new LinkedList<>();
        productions.add(getProductions(SEPARATOR_EMPTY));
        List<Node> deviations = children.stream().filter(Node::deviatesToNonterminal).collect(Collectors.toList());
        deviations.forEach(node -> productions.addAll(node.getProductionsWithDuplicates()));
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

    boolean deviatesToTerminal() {
        return getCountOfChildren() == 1 && getChild(0).isLeaf();
    }

    private boolean deviatesToNonterminal() {
        return !deviatesToTerminal() && isNode();
    }

    public void writeToLexicon(WordUsageCounter counter) {
        children.forEach(n -> {
            if (n.isLeaf()) {
                counter.addWord(n.getSymbol(), this.getSymbol());
                return;
            }
            n.writeToLexicon(counter);
        });
    }

}
