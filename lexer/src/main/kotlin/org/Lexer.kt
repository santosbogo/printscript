package org

import org.iterator.PrintScriptIterator
import java.io.Reader
import java.util.LinkedList
import java.util.Queue

class Lexer(
    private val lexicon: Lexicon,
    private val reader: Reader
) : PrintScriptIterator<Token> {
    private val currentTokens: Queue<Token> = LinkedList()
    private val position: Position = Position(1, 1)

    override fun hasNext(): Boolean {
        if (!currentTokens.isEmpty()) { // if tokens left in current statement
            return true
        }

        if (getNextChar() != -1) {
            lexNextStatement()
            return !currentTokens.isEmpty()
        }
        return false
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

    override fun peek(): Token? {
        if (currentTokens.isEmpty()) {
            lexNextStatement()
        }
        return currentTokens.peek()
    }

    // Esta función existe para poder leer el próximo char sin avanzar el reader.
    private fun getNextChar(): Int {
        reader.mark(1)
        val nextChar = reader.read()
        reader.reset()
        return nextChar
    }

    private fun lexNextStatement() {
        val statement = StringBuilder()
        var intChar = reader.read()

        while (intChar != -1) {
            val char = intChar.toChar()
            statement.append(char)
            if (char == ';') {
                break
            }
            intChar = reader.read()
        }

        if (statement.isNotEmpty()) {
            tokenizeStatement(statement.toString())
        }
    }

    private fun tokenizeStatement(statement: String) {
        val components = splitIgnoringLiterals(statement)
        for (component in components) {
            if (component.contains("\n")) {
                handleNewLine(position)
                continue
            } else if (component == " ") {
                position.column++
                continue
            }
            tokenizeComponent(component)
        }
    }

    private fun tokenizeComponent(component: String) {
        val subComponents = splitComponent(component)
        for (subComponent in subComponents) {
            try {
                val token = lexicon.getToken(subComponent, Location(position.line, position.column))
                currentTokens.add(token)
            } catch (e: Exception) {
                throw Exception(e.message ?: "Unknown error")
            }
            position.column += subComponent.length
        }
    }

    private fun handleNewLine(position: Position) {
        position.line++
        position.column = 1
    }

    private fun splitIgnoringLiterals(input: String): List<String> {
        val regex = Regex(
            "[a-zA-Z_][a-zA-Z0-9_]*|:|;|=|[0-9]+(?:\\.[0-9]+)?|\".*?\"|'.*?'|\\n|\\s+|[^\\s]"
        )
        return regex.findAll(input).map { it.value }.toList()
    }

    private fun splitComponent(component: String): List<String> {
        val regex = Regex("[a-zA-Z_][a-zA-Z0-9_]*|:|;|=|[0-9]+(?:\\.[0-9]+)?|\".*?\"|'.*?'|\\S")
        return regex.findAll(component).map { it.value }.toList()
    }

    fun collectAllTokens(): LexerResult {
        // copy the actual lexer, to simulate collection of tokens
        val newLexer = Lexer(lexicon, reader)

        val tokens = mutableListOf<Token>()
        val errors = mutableListOf<String>()

        // Collect all tokens from the lexer
        try {
            while (newLexer.hasNext()) {
                tokens.add(newLexer.next())
            }
        } catch (e: Exception) {
            errors.add(e.message ?: "Unknown error")
        }

        // Return a LexerResult object based on the collected tokens and errors
        val result = LexerResult()
        tokens.forEach { result.addToken(it) }
        errors.forEach { result.addError(it) }
        return result
    }
}
