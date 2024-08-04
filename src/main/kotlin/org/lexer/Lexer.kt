package org.lexer

import org.token.Token
import org.utils.Location

class Lexer {
    private val tokens = mutableListOf<Token>()

    fun lex(input: String, matcher: TokenMatcher): List<Token> {
        var index = 0
        var line = 0
        // Loop a lo largo del input
        while (index < input.length) {
            val char = input[index]
            // Chequeo cada caso y actualizo el index y la línea según corresponda
            when {
                char.isWhitespace() -> {
                    index++
                }
                char.isNewLine() -> {
                    line++
                }
                char.isLetter() -> {
                    val startIndex = index
                    val startLine = line
                    while (index < input.length && input[index].isLetter()) {
                        index++
                    }
                    matcher.addTokenIfExists(tokens, input.substring(startIndex, index), Location(startLine, startIndex, line, index)) }
                else -> {
                    throw IllegalArgumentException("Unexpected character: $char")
                }
            }
        }
        return tokens
    }
}

private fun Char.isNewLine(): Boolean {
    return this == '\n'
}
