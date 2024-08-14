package org

fun main() {
    val lexer = Lexer()
    val parser = Parser()
    val interpreter = Interpreter()

    while (true) {
        print("> ")
        val input = readLine() ?: break
        if (input.lowercase() == "exit") break

        try {
            val tokens = lexer.tokenize(input)
            val programNode = parser.parse(tokens)
            programNode.statements.forEach { interpreter.interpret(it) }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}
