package org.command

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.Formatter
import org.Lexer
import org.Parser
import java.io.File

class FormattingCommand(private val input: String) : Command {
    override fun execute() {
        // ./file.txt format format.json
        val parts = input.split(" ")
        val file = parts[0].removePrefix("./")
        val jsonFilePath = parts[2]

        val tokens = Lexer().tokenize(file)
        val ast = Parser().parse(tokens)

        val jsonContent = File(jsonFilePath).readText()
        val jsonObject = Json.parseToJsonElement(jsonContent).jsonObject

        val formatter = Formatter(ast, jsonObject)
        val formattedOutput = formatter.format()
        // TODO see what to do with the result
    }
}
