package org

import org.interpreter.Interpreter
import org.parser.Parser

fun main() {
    val lexiconFactory = LexiconFactory()
    val lexer = Lexer(lexiconFactory.createDefaultLexicon())
    val parser = Parser()
    val interpreter = Interpreter()

    while (true) {
        print("> ")
        val input = readLine() ?: break
        if (input.lowercase() == "exit") break

        try {
            val tokens = lexer.tokenize(input)
            val nodes = parser.parse(tokens)
            nodes.forEach { interpreter.interpret(it) }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}
