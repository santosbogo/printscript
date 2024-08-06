package org.lexer

import org.Location

class Lexer(private val lexicon: Lexicon) {

    fun tokenize(input: String): List<Token> {
        // Tokenize input string into a list of tokens.
        // It also keeps track of the line and column of each token for the Location
        val tokens = ArrayList<Token>()
        val statements = splitStatements(input)
        var line = 1
        var column = 1

        for (statement in statements) {
            // A statement is a line of code that ends with a semicolon.
            if (statement.isEmpty()) {
                continue
            }

            val components = splitIgnoringLiterals(statement)
            for (component in components) {
                // A component is a part of a statement separated by spaces.
                if (component.contains("\n")) {
                    line++
                    column = 1
                }

                val subComponents = splitComponent(component)
                for (subComponent in subComponents) {
                    // A subcomponent is a part of a component separated by special characters.
                    //We ask the lexicon to create a token for each subcomponent. Lexicon == Dictionary of tokens
                    tokens.add(lexicon.getToken(subComponent, Location(line, column)))
                    column += subComponent.length
                }
                column++
            }
        }

        return tokens
    }

    private fun splitStatements(input: String): List<String> {
        // Separate statements by semicolons. Each statement should be a mini astnode in the end of parser.
        val regex = Regex("([^;]+;)")
        return regex.findAll(input).map { it.value }.toList()
    }

    private fun splitComponent(component: String): List<String> {
        // Separate components in subcomponents in cases such as 'a:' or 10;
        // If component is at its minimum unit, does not split it.
        val regex = Regex("([a-zA-Z][a-zA-Z0-9]*|:|[0-9]+|\".*\"|\\S)")
        return regex.findAll(component).map { it.value }.toList()
    }

    private fun splitIgnoringLiterals(input: String): List<String> {
        // Split input by spaces, but ignore spaces inside literals strings.
        val regex = Regex("\"[^\"]*\"|'[^']*'|\\S+")
        return regex.findAll(input).map { it.value }.toList()
    }
}