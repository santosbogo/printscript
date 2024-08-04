package org.lexer

import org.token.Token
import org.tokenTypes.IdentifierType
import org.tokenTypes.LetType
import org.tokenTypes.TokenType
import org.utils.Location

class TokenMatcher {
    private val dictionary = mutableMapOf<String, TokenType>()
    private var wasLet = false

    fun addTokenIfExists(tokens: MutableList<Token>, substring: String, location: Location) {
        val type: TokenType? = dictionary[substring]
        // Always after a let there is identifier
        if (wasLet) {
            tokens.add(IdentifierType().generateToken(substring, location))
            wasLet = false
        } else if (type != null) { // Else, if the token exists, add it to the list
            if (type == LetType()) { wasLet = true }
            tokens.add(type.generateToken(substring, location))
        } else {
            throw IllegalArgumentException("Unexpected token: $substring")
        }
    }

    fun addNewToken(token: TokenType) {
        dictionary[token.name] = token
    }

    private fun deleteToken(token: TokenType) {
        dictionary.remove(token.name)
    }
}