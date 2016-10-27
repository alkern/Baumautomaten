package parser;

import model.Node;

public class RecursiveLineParser {

    private int position;
    private String line;
    private char debug;

    public Node parseLineToTree(String line) {
        this.position = 0;
        this.line = line;
        return parse();
    }

    private Node parse() {
        assertBeginsWithBracket();
        position++;
        String symbol = readSymbol(position);
        Node result = new Node(symbol);
        skipChars(' ');

        while (currentChar() == '(') {
            skipChars(' ');
            result.addChild(parse());
        }

        if (currentChar() == ')') {
            position++;
            return result;
        }

        if (isPositionInRange()) {
            result.addChild(new Node(readSymbol(position)));
        }
        skipChars(' ', ')');
        return result;
    }

    private void assertBeginsWithBracket() {
        if (currentChar() != '(') throw new RuntimeException("Input has to start with (");
    }

    private char currentChar() {
        try {
            debug = line.charAt(position);
            return line.charAt(position);
        } catch (StringIndexOutOfBoundsException e) {
            return '$';
        }
    }

    private void skipChars(char... toSkip) {
        while (toSkipContainsCurrent(toSkip)) {
            position++;
        }
    }

    private boolean toSkipContainsCurrent(char[] toSkip) {
        for (char c : toSkip) {
            if (c == currentChar()) {
                return true;
            }
        }
        return false;
    }

    private String readSymbol(int startPosition) {
        skipToChars(' ', '(', ')');
        return line.substring(startPosition, position);
    }

    private void skipToChars(char... toFind) {
        while (!toSkipContainsCurrent(toFind) && isPositionInRange()) {
            position++;
        }
    }

    private boolean isPositionInRange() {
        return position < line.length();
    }
}
