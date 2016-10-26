package parser;

import model.Node;

public class RecursiveLineParser {

    private int position;
    private String line;

    public RecursiveLineParser() {
        this.position = 0;
    }

    public Node parseLineToTree(String line) {
        this.line = line;
        assertBeginsWithBracket();
        return parse();
    }

    private void assertBeginsWithBracket() {
        if (currentChar() != '(') throw new RuntimeException("Input has to start with (");
    }

    private char currentChar() {
        return line.charAt(position);
    }

    private Node parse() {
        skipSpecialSigns();
        String symbol = readSymbol(position);
        Node result = new Node(symbol);
        skipWhitespace();

        if (currentChar() == '(') {
            position++;
            result.addChild(parse());
        }

        if (currentChar() == ')') {
            position++;
            return result;
        }

        result.addChild(parse());
        return result;
    }

    private void skipWhitespace() {
        while (currentChar() == ' ') {
            position++;
        }
    }

    private void skipSpecialSigns() {
        while (isCurrentCharSpecialSign()) {
            position++;
        }
    }

    private boolean isCurrentCharSpecialSign() {
        return currentChar() == ' ' || currentChar() == '(';
    }

    private String readSymbol(int startPosition) {
        while (!isCurrentCharSpecialSign() && currentChar() != ')') {
            position++;
        }
        return line.substring(startPosition, position);
    }
}
