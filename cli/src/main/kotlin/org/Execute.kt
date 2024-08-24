package org

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import java.io.File

class Execute : CliktCommand() {
    private val filePath by argument(help = "Path to the script file to execute")

    override fun run() {
        val code = File(filePath).readText()
        val lexerResult = Lexer().tokenize(code)

        if (lexerResult.hasErrors()) {
            lexerResult.errors.forEach { echo("Error: $it") }
            return
        }

        val parserResult = Parser().parse(lexerResult.tokens)

        if (parserResult.programNode == null) {
            parserResult.errors.forEach { echo("Error: $it") }
            return
        }

        Interpreter().interpret(parserResult.programNode!!)

        echo("Execution successful")
    }
}
