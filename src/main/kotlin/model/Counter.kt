package model

import java.util.*

class Counter<V : Comparable<V>> {

    private val table: MutableMap<V, Int> = LinkedHashMap()

    fun addValue(value: V) {
        table[value] = getCounterFor(value) + 1
    }

    fun getCounterFor(value: V): Int {
        return table[value] ?: 0
    }

    val sum: Int
        get() = table.values.sum()

    val values: MutableSet<V>
        get() = table.keys

    val size: Int
        get() = table.size

    val sorted: SortedMap<V, Int>
        get() = table.toSortedMap()
}