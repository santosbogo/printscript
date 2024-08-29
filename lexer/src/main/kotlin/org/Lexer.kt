package org

class Lexer(private val lexicon: Lexicon = LexiconFactory().createDefaultLexicon()) {

    fun tokenize(input: String): LexerResult {
        val result = LexerResult()
        val statements = splitStatements(input)
        val position = Position(1, 1)

        for (statement in statements) {
            if (statement.isEmpty()) continue
            tokenizeStatement(statement, result, position)
        }

        return result
    }

    private fun tokenizeStatement(statement: String, result: LexerResult, position: Position) {
        val components = splitIgnoringLiterals(statement)
        for (component in components) {
            if (component.contains("\n")) {
                handleNewLine(position)
            }
            tokenizeComponent(component, result, position)
        }
    }

    private fun tokenizeComponent(component: String, result: LexerResult, position: Position) {
        val subComponents = splitComponent(component)
        for (subComponent in subComponents) {
            // Try to get a token from the lexicon. If it fails, add an error and a token with type "UnknownToken".
            try {
                val token = lexicon.getToken(subComponent, Location(position.line, position.column))
                result.addToken(token)
            } catch (e: Exception) {
                result.addError(e.message ?: "Unknown error")
                result.addToken(Token("UnknownToken", subComponent, Location(position.line, position.column)))
            }
            position.column += subComponent.length
        }
        position.column++
    }

    private fun handleNewLine(position: Position) {
        position.line++
        position.column = 0
    }

    private fun splitStatements(input: String): List<String> {
        var result: List<String> = ArrayList()
        var statement = ""
        for (word in input) {
            statement += word
            if (word == ';') {
                result = ArrayList(result + statement)
                statement = ""
            }
        }
        result = ArrayList(result + statement)
        return result
    }

    private fun splitIgnoringLiterals(input: String): List<String> {
        val regex = Regex("\"[^\"]*\"|'[^']*'|[^\\s\"']+|\\n")
        return regex.findAll(input).map { it.value }.toList()
    }

    private fun splitComponent(component: String): List<String> {
        val regex = Regex("[a-zA-Z_][a-zA-Z0-9_]*|:|;|=|[0-9]+|\".*?\"|'.*?'|\\S")
        return regex.findAll(component).map { it.value }.toList()
    }
}
