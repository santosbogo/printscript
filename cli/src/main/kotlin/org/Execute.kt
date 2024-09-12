package org

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import java.io.BufferedReader
import java.io.File

import java.io.FileInputStream

class Execute : CliktCommand() {
    private val filePath by argument(help = "Path to the script file to execute")

    override fun run() {
        val file = File(filePath)
        val totalBytes = file.length()
        val inputStream = ProgressInputStream(FileInputStream(file), totalBytes)
        val reader = BufferedReader(inputStream.reader())

        val lexer = LexerFactory.createLexerV11(reader)
        val parser = ParserFactory.createParserV11(lexer)
        val interpreter = InterpreterFactory.createCliInterpreterV11(parser)

        interpreter.interpret()

        echo("Execution successful")
    }
}
