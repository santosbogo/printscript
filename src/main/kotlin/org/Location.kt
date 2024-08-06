package org

class Location(
    private val line: Int,
    private val column: Int
) {
    override fun toString(): String {
        return "(line: $line, column: $column)"
    }
}