package org

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import java.io.File
import java.io.FileInputStream

class Analyze : CliktCommand() {
    private val filePath by argument(help = "Path to the script file to execute")
    private val rulePath by argument(help = "Path to the rule json to use")

    override fun run() {
        val code = File(filePath).readText()
        val inputStream = FileInputStream(code)
        val rulesContent = File(rulePath).readText()
        val rules = Json.parseToJsonElement(rulesContent).jsonObject

        echo("Lexing...\n", trailingNewline = true)
        val lexer = LexerFactory.createLexerV11(inputStream)

        echo("Parsing...\n", trailingNewline = true)
        val parser = ParserFactory.createParserV11(lexer)

        echo("Analyzing...\n", trailingNewline = true)
        val linter = LinterFactory().createLinterV11(parser)
        val linterResult = linter.lint(rules)
        linterResult.getList().forEach { echo(it, true) }

        echo("Analyze successful")
    }
}
