package parser;

import java.util.LinkedList;
import java.util.List;

public class Tokenizer {

    private String line;
    private int position;

    public List<String> scan(String line) {
        this.position = 0;
        this.line = line;
        List<String> tokens = new LinkedList<>();
        assertBeginsWithBracket();

        while (isPositionInRange()) {
            tokens.add(nextToken());
        }

        return tokens;
    }

    private String nextToken() {
        skipChars(' ');
        if (currentChar() == '(') {
            position++;
            return "(";
        }
        if (currentChar() == ')') {
            position++;
            return ")";
        }
        return readSymbol(position);
    }

    private void assertBeginsWithBracket() {
        if (currentChar() != '(') throw new RuntimeException("Input has to start with (");
    }

    private char currentChar() {
        try {
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
