package model

class RegularRule(val symbol: String, val separator: String, val body: String) : Comparable<RegularRule> {
    private val PREFIX = "q_"

    override fun toString(): String {
        return "$PREFIX$symbol$separator$body".trim()
    }

    override fun compareTo(other: RegularRule): Int {
        return this.toString().compareTo(other.toString())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        return this.toString() == other.toString()
    }

    override fun hashCode(): Int {
        var result = symbol.hashCode()
        result = 31 * result + separator.hashCode()
        result = 31 * result + body.hashCode()
        result = 31 * result + PREFIX.hashCode()
        return result
    }


}