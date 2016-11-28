package parser

import java.util.*

class Tokenizer {

    private var line: String? = null
    private var position: Int = 0

    fun scan(line: String): List<String> {
        this.position = 0
        this.line = line
        val tokens = LinkedList<String>()
        assertBeginsWithBracket()

        while (isPositionInRange) {
            tokens.add(nextToken())
        }

        return tokens
    }

    private fun nextToken(): String {
        skipChars(' ')
        if (currentChar() == '(') {
            position++
            return "("
        }
        if (currentChar() == ')') {
            position++
            return ")"
        }
        return readSymbol(position)
    }

    private fun assertBeginsWithBracket() {
        if (currentChar() != '(') throw RuntimeException("Input has to start with (")
    }

    private fun currentChar(): Char {
        return line!![position]
    }

    private fun skipChars(vararg toSkip: Char) {
        while (toSkipContainsCurrent(toSkip)) {
            position++
        }
    }

    private fun toSkipContainsCurrent(toSkip: CharArray): Boolean {
        for (c in toSkip) {
            if (c == currentChar()) {
                return true
            }
        }
        return false
    }

    private fun readSymbol(startPosition: Int): String {
        skipToChars(' ', '(', ')')
        return line!!.substring(startPosition, position)
    }

    private fun skipToChars(vararg toFind: Char) {
        while (!toSkipContainsCurrent(toFind) && isPositionInRange) {
            position++
        }
    }

    private val isPositionInRange: Boolean
        get() = position < line!!.length
}
