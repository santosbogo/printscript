package org.lexer

import org.Location
import org.tokens.Token

class Lexer {
    private val tokenMap = listOf(
        "let" to "DeclarationToken",
        "=" to "AssignationToken",
        ";" to "SemicolonToken",
        ":" to "ColonToken",
        "number" to "NumberTypeToken",
        "string" to "StringTypeToken",
        "[0-9]+" to "NumberToken",
        "\".*\"" to "StringToken",
        "[a-zA-Z][a-zA-Z0-9]*" to "IdentifierToken"
    )

    fun tokenize(input: String): List<Token> {
        val tokens = ArrayList<Token>()
        val statements = splitStatements(input)
        var line = 1
        var column = 1

        for (statement in statements) {
            if (statement.isEmpty()) {
                continue
            }

            val components = statement.split(" ")
            for (component in components) {
                if (component.contains("\n")) {
                    line++
                    column = 1
                }

                val subComponents = splitComponent(component)
                for (subComponent in subComponents) {
                    tokens.add(getToken(subComponent, line, column))
                    column += subComponent.length
                }
                column++
            }
        }

        return tokens
    }

    private fun splitStatements(input: String): List<String> {
        val regex = Regex("([^;]+;)")
        return regex.findAll(input).map { it.value }.toList()
    }

    private fun splitComponent(component: String): List<String> {
        val regex = Regex("([a-zA-Z][a-zA-Z0-9]*|:|[0-9]+|\".*\"|\\S)")
        return regex.findAll(component).map { it.value }.toList()
    }

    private fun getToken(component: String, line: Int, column: Int): Token {
        val tokenType = tokenMap.firstOrNull { (pattern, _) ->
            Regex(pattern).matches(component)
        }?.second ?: "UnknownToken"

        return Token(
            type = tokenType,
            value = component,
            location = Location(line, column)
        )
    }
}