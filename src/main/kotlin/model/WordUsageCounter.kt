package model

import java.util.*

class WordUsageCounter() {

    private val wordCounter: LinkedHashMap<String, LinkedHashMap<String, Int>>

    init {
        wordCounter = LinkedHashMap<String, LinkedHashMap<String, Int>>()
    }

    constructor(tree: List<Node>) : this() {

    }

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
}


