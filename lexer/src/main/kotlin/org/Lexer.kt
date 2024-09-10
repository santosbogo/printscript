package org

import java.io.BufferedReader
import java.io.Reader
import java.util.LinkedList
import java.util.Queue

class Lexer(private val lexicon: Lexicon, private val reader: Reader) : Iterator<Token> {
    private var currentIndex: Int = 0 // indice en el input string.
    private var currentTokens: Queue<Token> = LinkedList() // tokens q tokenize al llamar a next()
    private var position: Position = Position(1, 1)

    private fun tokenizeStatement(statement: String, position: Position) {
        val components = splitIgnoringLiterals(statement)
        for (component in components) {
            if (component.contains("\n")) {
                handleNewLine(position)
                continue
            } else if (component == " ") {
                position.column++
                continue
            }
            tokenizeComponent(component, position)
        }
    }

    private fun tokenizeComponent(component: String, position: Position) {
        val subComponents = splitComponent(component)
        for (subComponent in subComponents) {
            try {
                val token = lexicon.getToken(subComponent, Location(position.line, position.column))
                currentTokens.add(token)
            } catch (e: Exception) {
                throw Exception(e.message ?: "Unknown error")
                currentTokens.add(Token("UnknownToken", subComponent, Location(position.line, position.column)))
            }
            position.column += subComponent.length
        }
    }

    private fun handleNewLine(position: Position) {
        position.line++
        position.column = 1
    }

    private fun splitIgnoringLiterals(input: String): List<String> {
        val regex = Regex("[a-zA-Z_][a-zA-Z0-9_]*|:|;|=|[0-9]+(?:\\.[0-9]+)?|\".*?\"|'.*?'|\\n|\\s+|[^\\s]")
        return regex.findAll(input).map { it.value }.toList()
    }

    private fun splitComponent(component: String): List<String> {
        val regex = Regex("[a-zA-Z_][a-zA-Z0-9_]*|:|;|=|[0-9]+(?:\\.[0-9]+)?|\".*?\"|'.*?'|\\S")
        return regex.findAll(component).map { it.value }.toList()
    }

    override fun hasNext(): Boolean {
        if (!currentTokens.isEmpty()) { // if tokens left in current statement
            return true
        }
        return getNextChar() != -1 // Indica que se acabó el reader
    }

    // Esta función existe para poder leer el próximo char sin avanzar el reader.
    private fun getNextChar(): Int {
        reader.mark(1)
        val nextChar = reader.read()
        reader.reset()
        return nextChar
    }

    override fun next(): Token {
        if (!hasNext()) {
            throw NoSuchElementException()
        }

        // Se acabaron los tokens de la statement actual. Lexeo el próximo.
        if (currentTokens.isEmpty()) {
            lexNextStatement()
        }

        // return next token from current statement
        return currentTokens.remove()
    }

    private fun lexNextStatement() {
        val statement = StringBuilder()
        // Leo hasta encontrar ";" con un reader
        val bufferedReader = BufferedReader(reader)

        var currentCharInt: Int
        var currentChar: Char

        // Read characters one by one
        while (bufferedReader.read().also { currentCharInt = it } != -1) {
            currentChar = currentCharInt.toChar()

            statement.append(currentChar)
            currentIndex++

            // End of statement
            if (currentChar == ';') {
                // Tokenize the current statement
                tokenizeStatement(statement.toString(), position)

                // Clear the StringBuilder for the next statement
                statement.clear()
            }
        }

        // If there's any remaining part of the statement after the last semicolon, tokenize it
        if (statement.isNotEmpty()) {
            tokenizeStatement(statement.toString(), position)
        }
    }

    fun collectAllTokens(): LexerResult {
        // copy the actual lexer, to simulate collection of tokens
        val newLexer = Lexer(lexicon, reader)

        val tokens = mutableListOf<Token>()
        val errors = mutableListOf<String>()

        // Collect all tokens from the lexer
        while (newLexer.hasNext()) {
            try {
                tokens.add(newLexer.next())
            } catch (e: Exception) {
                errors.add(e.message ?: "Unknown error")
            }
        }

        // Return a LexerResult object based on the collected tokens and errors
        val result = LexerResult()
        tokens.forEach { result.addToken(it) }
        errors.forEach { result.addError(it) }
        return result
    }
}
