package org.lexer

import org.Location

class Lexer(private val lexicon: Lexicon) {

    fun tokenize(input: String): List<Token> {
        val tokens = ArrayList<Token>()
        val statements = splitStatements(input)
        var line = 1
        var column = 1

        for (statement in statements) {
            if (statement.isEmpty()) {
                continue
            }

            val components = splitIgnoringLiterals(statement)
            for (component in components) {
                if (component.contains("\n")) {
                    line++
                    column = 1
                }

                val subComponents = splitComponent(component)
                for (subComponent in subComponents) {
                    tokens.add(lexicon.getToken(subComponent, Location(line, column)))
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

    private fun splitIgnoringLiterals(input: String): List<String> {
        val regex = Regex("\"[^\"]*\"|'[^']*'|\\S+")
        return regex.findAll(input).map { it.value }.toList()
    }
}