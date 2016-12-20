package model

class RegularRule(val symbol: String, val separator: String, val body: String) : Comparable<RegularRule> {
    private val PREFIX = "q_"

    override fun toString(): String {
        return "$PREFIX$symbol $separator $body".trim()
    }

    override fun compareTo(other: RegularRule): Int {
        return this.toString().compareTo(other.toString())
    }
}