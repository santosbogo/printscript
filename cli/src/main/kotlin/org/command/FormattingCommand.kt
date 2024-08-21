package org.command

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.Formatter
import org.Lexer
import org.Parser
import java.io.File

class FormattingCommand(
    private val input: String,
    private val lexer: Lexer,
    private val parser: Parser,
) : Command {
    override fun execute() {
        // format file.txt file.json
        val parts = input.split(" ")
        val file = parts[1]
        val jsonFilePath = parts[2]

        val tokens = lexer.tokenize(file)
        val ast = parser.parse(tokens)

        val jsonContent = File(jsonFilePath).readText()
        val jsonObject = Json.parseToJsonElement(jsonContent).jsonObject

        val formatter = Formatter(ast, jsonObject)
        val formattedOutput = formatter.format()
        // TODO see what to do with the result
    }
}
