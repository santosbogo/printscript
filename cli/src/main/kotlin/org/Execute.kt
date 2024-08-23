package org

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import java.io.File

class Execute : CliktCommand() {
    val filePath by argument()
    override fun run() {
        val code = File(filePath).readText()
        val lexerResult = Lexer().tokenize(code)

        if (lexerResult.hasErrors()) {
            lexerResult.errors.forEach { echo(it) }
            return
        }

        val ast = Parser().parse(lexerResult.tokens)
        val result = Interpreter().interpret(ast)
    }
}
