package org.lexer

import org.Location

class Lexicon(private val tokenMap: List<Pair<String, String>>) {
    fun getToken(component: String, location: Location): Token {
        // Get the token type for a given component from its tokenMap
        val tokenType = tokenMap.firstOrNull { (pattern, _) ->
            component.matches(Regex(pattern))
        }?.second ?: throw Exception("No token found for component: $component at $location")

        return Token(
            type = tokenType,
            value = component,
            location = location
        )
    }

    fun addToken(pattern: String, tokenType: String): Lexicon {
        // Add a new token to the lexicon. Not sure if it's a good idea to allow this.
        return Lexicon(tokenMap + Pair(pattern, tokenType))
    }
}

class LexiconFactory {
    fun createDefaultLexicon(): Lexicon {
        // Create a default lexicon with some common tokens. Its the basic lexicon.
        return Lexicon(
            listOf(
                "let" to "DeclarationToken",

                "=" to "AssignationToken",
                ";" to "SemicolonToken",
                ":" to "ColonToken",
                "\\+" to "PlusToken",
                "\\-" to "MinusToken",
                "\\*" to "ProductToken",
                "/" to "DivisionToken",

                "number" to "NumberTypeToken",
                "string" to "StringTypeToken",

                "[0-9]+" to "NumberToken",
                "[0-9]+\\.[0-9]+" to "NumberToken",
                "\".*\"" to "StringToken",
                "\'.*\'" to "StringToken",

                "[a-zA-Z_][a-zA-Z0-9_]*" to "IdentifierToken"
            )
        )
    }
}