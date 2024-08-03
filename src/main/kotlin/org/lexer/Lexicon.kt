package org.lexer

import org.Location

class Lexicon(private val tokenMap: List<Pair<String, String>>) {

    fun getToken(component: String, location: Location): Token {
        val tokenType = tokenMap.firstOrNull { (pattern, _) ->
            component.matches(Regex(pattern))
        }?.second ?: "UnknownToken"

        return Token(
            type = tokenType,
            value = component,
            location = location
        )
    }

    fun addToken(pattern: String, tokenType: String): Lexicon {
        return Lexicon(tokenMap + Pair(pattern, tokenType))
    }
}

class LexiconFactory {
    fun createDefaultLexicon(): Lexicon {
        return Lexicon(
            listOf(
                "let" to "DeclarationToken",

                "=" to "AssignationToken",
                ";" to "SemicolonToken",
                ":" to "ColonToken",
                "+" to "PlusToken",
                "-" to "MinusToken",
                "*" to "ProductToken",
                "/" to "DivisionToken",

                "number" to "NumberTypeToken",
                "string" to "StringTypeToken",

                "[0-9]+" to "NumberToken",
                "[0-9]+\\.[0-9]+" to "NumberToken",
                "\".*\"" to "StringToken",
                "\'.*\'" to "StringToken",

                "[a-zA-Z][a-zA-Z0-9]*" to "IdentifierToken"
            )
        )
    }
}