package parser;

import model.Node;

import java.util.List;

public class RecursiveLineParser {

    private int position;
    private List<String> tokens;

    public Node parseLineToTree(List<String> tokens) {
        this.position = 1;
        this.tokens = tokens;
        return parse();
    }

    private Node parse() {
        Node result = new Node(currentToken());
        position++;

        while (!currentToken().equals(")")) {
            if (currentToken().equals("(")) {
                position++;
                result.addChild(parse());
            } else {
                result.addChild(new Node(currentToken()));
                position++;
            }
        }

        position++;
        return result;
    }

    private String currentToken() {
        return tokens.get(position);
    }

}
