package model

import java.util.*

class WordUsageCounter(trees: MutableList<Node>) {

    private val wordCounter: LinkedHashMap<String, LinkedHashMap<String, Int>> = LinkedHashMap()

    init {
        trees.forEach { it.writeToLexicon(this) }
    }

    val size: Int
        get() = wordCounter.size

    fun addWord(word: String, nonterminal: String) {
        if (!wordCounter.containsKey(word)) {
            wordCounter[word] = addCounter(nonterminal)
            return
        }

        val nonterminalCounter = wordCounter[word]!!
        if (nonterminalCounter.containsKey(nonterminal)) {
            val count = nonterminalCounter[nonterminal]
            nonterminalCounter[nonterminal] = Integer.valueOf(count!!.plus(1))
            return
        }
        nonterminalCounter.put(nonterminal, 1)
    }

    private fun addCounter(nonterminal: String): LinkedHashMap<String, Int> {
        val nonterminalCounter = LinkedHashMap<String, Int>()
        nonterminalCounter[nonterminal] = 1
        return nonterminalCounter
    }

    fun getCountFor(word: String, nonterminal: String): Int {
        return wordCounter[word]?.getOrDefault(nonterminal, 0) ?: 0
    }

    fun asStringList(): MutableList<String> {
        val content = LinkedList<String>()

        wordCounter.forEach { word, occurences ->
            run {
                val builder = StringBuilder()
                builder.append(word)
                builder.append("\t")
                occurences.forEach { nonterminal, count -> builder.append("$nonterminal $count ") }
                content.add(builder.toString().trim())
            }
        }

        return content
    }
}


