package parser;

import model.Node;

import java.util.List;

public class RecursiveLineParser {

    private int position;
    private List<String> tokens;

    public Node parseLineToTree(List<String> tokens) {
        this.position = 0;
        this.tokens = tokens;
        return parse();
    }

    private Node parse() {
        Node result = null;


        return result;
    }

    private String currentToken() {
        return tokens.get(position);
    }

}
