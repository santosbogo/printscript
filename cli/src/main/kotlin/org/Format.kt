package org

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import java.io.File
import java.io.StringReader

class Format : CliktCommand() {
    private val filePath by argument(help = "Path to the script file to execute")
    private val rulePath by argument(help = "Path to the rule json to use")

    override fun run() {
        val code = File(filePath).readText()
        val reader = StringReader(code)
        val rulesContent = File(rulePath).readText()
        val rules = Json.parseToJsonElement(rulesContent).jsonObject

        echo("Lexing...\n", trailingNewline = true)
        val lexer = LexerFactory.createLexerV11(reader)

        echo("Parsing...\n", trailingNewline = true)
        val parser = ParserFactory.createParserV11(lexer)

        echo("Formatting...\n", trailingNewline = true)
        val formatRules = RulesFactory().getRules(rules.toString(), "1.1")
        val formatResult = Formatter(parser).format(formatRules)
        File(filePath).writeText(formatResult.code)

        echo("Format successful")
    }
}
