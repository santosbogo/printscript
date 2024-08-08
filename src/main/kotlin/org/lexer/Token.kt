package org.lexer

import org.Location

class Token(
    val type: String,
    val value: String,
    val location: Location
) {
    override fun toString(): String {
        return "Token($type($value) in $location)"
    }
}
