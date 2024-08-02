package org.example

import org.example.token.Token
import org.example.tokenTypes.TokenType
import org.example.utils.Location
import java.util.Dictionary

class Lexer {
    // A SEGUIR ...

    /*
    val dictionary : Dictionary<String, TokenType> = Dictionary<String, TokenType>()
    val tokens = mutableListOf<Token>()

    private fun addTokenIfExists(substring: String, location: Location) {
        val word : TokenType = dictionary.get(substring)
        if (word != null) {
            tokens.add(Token(word, substring, location))
        } else {
            throw IllegalArgumentException("Unexpected token: $substring")
        }
    }

    fun lex(input: String): List<Token> {
        var index = 0
        var line = 0
        while (index < input.length) {
            val char = input[index]
            when {
                char.isWhitespace() -> {
                    index++
                }
                // Check if char is \n and line++
                char.isLetter() -> {
                    val startIndex = index
                    val startLine = line
                    while (index < input.length && input[index].isLetter()) {
                        index++
                        // Check if char is \n and line++
                    }
                    addTokenIfExists(input.substring(startIndex, index), Location(startIndex, index, startLine, line)) }
                else -> {
                    throw IllegalArgumentException("Unexpected character: $char")
                }
            }
        }
        return tokens
    }

     */
}