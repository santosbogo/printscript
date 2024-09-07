package org

class Lexer(private val lexicon: Lexicon): Iterator<Token> {
    private var currentIndex: Int = 0 // indice en el input string.
    private var currentTokens: List<Token> = ArrayList() // tokens q tokenize al llamar a next()
    private var currentTokenIndex: Int = 0 // q token retorne la última vez, y donde quedé parado.
    private var position: Position = Position(1, 1)
    private var input: String = "" // inizializo para usar desp.
    private var result = LexerResult()

    /*fun tokenize(input: String): LexerResult {
        this.input = input
        val result = LexerResult()
        val statements = splitStatements(input)
        val position = Position(1, 1)

        for (statement in statements) {
            if (statement.isEmpty()) continue
            tokenizeStatement(statement, position)
        }

        return result
    }*/

    private fun tokenizeStatement(statement: String, position: Position) {
        val components = splitIgnoringLiterals(statement)
        for (component in components) {
            if (component.contains("\n")) {
                handleNewLine(position)
            }
            tokenizeComponent(component, position)
        }
    }

    private fun tokenizeComponent(component: String, position: Position) {
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
        for (character in input) {
            statement += character
            if (character == ';') {
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
        val regex = Regex("[a-zA-Z_][a-zA-Z0-9_]*|:|;|=|[0-9]+(?:\\.[0-9]+)?|\".*?\"|'.*?'|\\S")
        return regex.findAll(component).map { it.value }.toList()
    }

    override fun hasNext(): Boolean {
        // if tokens left in currrent statement
        if (currentTokenIndex < currentTokens.size) {
            return true
        }

        // there are characters in input left.
        return currentIndex < input.length
    }

    override fun next(): Token {
        if (!hasNext()) {
            throw NoSuchElementException()
        }

        // se acabaron los tokens de la statement actual. lexeo el próximo.
        if (currentTokenIndex >=currentTokens.size) {
            lexNextStatement()
        }

        // return next token from current statement
        val token = currentTokens[currentTokenIndex]
        currentTokenIndex++ // Move to the next token
        return token
    }

    private fun lexNextStatement() {
        val statement = StringBuilder()

        // leo hassta encontrar ";"
        while (currentIndex < input.length) {
            val currentChar = input[currentIndex]

            statement.append(currentChar)
            currentIndex++

            // end of statement
            if (currentChar == ';') {
                break
            }

            //actualizo posición.
            position = if (currentChar == '\n') {
                position.copy(line = position.line + 1, column = 0)
            } else {
                position.copy(column = position.column + 1)
            }

        tokenizeStatement(statement.toString(), position)
        currentTokens = result.tokens // tokens de la statement tokenizada..
        currentTokenIndex = 0 // Reset the token index for the new statement
        }
    }
}
