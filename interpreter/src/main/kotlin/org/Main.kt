package org

fun main() {
    val lexer = Lexer()
    val parser = Parser()
    val interpreter = Interpreter()

    while (true) {
        print("- ")
        val input = readLine() ?: break
        if (input.lowercase() == "exit") break

        try {
            val lexerResult = lexer.tokenize(input)
            val parserResult = parser.parse(lexerResult.tokens)
            parserResult.programNode?.statements?.forEach { interpreter.interpret(it) }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}
