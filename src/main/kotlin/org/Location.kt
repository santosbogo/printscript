package org

class Location(
    val line: Int,
    val column: Int
) {
    override fun toString(): String {
        return "Location(line= $line, column= $column)"
    }
}