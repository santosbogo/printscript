package org.command

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.Formatter
import org.Lexer
import org.Linter
import org.Parser
import java.io.File

class AnalyzingCommand(private val input: String) : Command {
    override fun execute() {
        // ./file.txt analyze format.json
        val parts = input.split(" ")
        val file = parts[0].removePrefix("./")
        val jsonFilePath = parts[2]

        val tokens = Lexer().tokenize(file)
        val ast = Parser().parse(tokens)

        val jsonContent = File(jsonFilePath).readText()
        val jsonObject = Json.parseToJsonElement(jsonContent).jsonObject

        // val linter = Linter()
        // val lintedOutput = linter.lint(ast)
        // TODO see what to do with the result
    }
}