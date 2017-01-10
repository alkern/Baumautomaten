package parser

import model.Node

class RecursiveLineParser {

    private var position: Int = 0
    private var tokens: List<String>? = null

    fun parseLinesToTree(tokens: List<String>): Node {
        this.position = 1
        this.tokens = tokens
        return parse()
    }

    private fun parse(): Node {
        val result = Node(currentToken())
        position++

        while (currentToken() != ")") {
            if (currentToken() == "(") {
                position++
                result.addChild(parse())
            } else {
                result.addChild(Node(currentToken()))
                position++
            }
        }

        position++
        return result
    }

    private fun currentToken(): String {
        return tokens!![position]
    }

}
