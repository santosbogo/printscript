package org

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import java.io.File
import java.io.FileInputStream

class Execute : CliktCommand() {
    private val filePath by argument(help = "Path to the script file to execute")

    override fun run() {
        val code = File(filePath).readText()
        val inputStream = FileInputStream(code)

        echo("Lexing...\n", trailingNewline = true)
        val lexer = LexerFactory.createLexerV11(inputStream)

        echo("Parsing...\n", trailingNewline = true)
        val parser = ParserFactory.createParserV11(lexer)

        echo("Executing...\n", trailingNewline = true)
        val interpreter = InterpreterFactory.createCliInterpreterV11(parser)
        val interpreterResult = interpreter.interpret()

        echo("Execution successful")
    }
}
