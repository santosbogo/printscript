package org

import org.shared.Location
import org.common.Token


class Lexer(private val lexicon: Lexicon = LexiconFactory().createDefaultLexicon()) {

    fun tokenize(input: String): List<Token> {
        val tokens = ArrayList<Token>()
        val statements = splitStatements(input)
        val position = Position(1, 1)

        for (statement in statements) {
            if (statement.isEmpty()) continue
            tokenizeStatement(statement, tokens, position)
        }

        return tokens
    }

    private fun tokenizeStatement(statement: String, tokens: ArrayList<Token>, position: Position) {
        val components = splitIgnoringLiterals(statement)
        for (component in components) {
            if (component.contains("\n")) {
                handleNewLine(position)
            }
            tokenizeComponent(component, tokens, position)
        }
    }

    private fun tokenizeComponent(component: String, tokens: ArrayList<Token>, position: Position) {
        val subComponents = splitComponent(component)
        for (subComponent in subComponents) {
            tokens.add(lexicon.getToken(subComponent, Location(position.line, position.column)))
            position.column += subComponent.length
        }
        position.column++
    }

    private fun handleNewLine(position: Position) {
        position.line++
        position.column = 0
    }

    private fun splitStatements(input: String): List<String> {
        val regex = Regex("[^;]+;")
        return regex.findAll(input).map { it.value }.toList()
    }

    private fun splitIgnoringLiterals(input: String): List<String> {
        val regex = Regex("\"[^\"]*\"|'[^']*'|[^\\s\"']+|\\n")
        return regex.findAll(input).map { it.value }.toList()
    }

    private fun splitComponent(component: String): List<String> {
        val regex = Regex("[a-zA-Z_][a-zA-Z0-9_]*|:|;|=|[0-9]+|\".*?\"|\\S")
        return regex.findAll(component).map { it.value }.toList()
    }
}
