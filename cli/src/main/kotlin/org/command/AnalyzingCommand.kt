package org.command

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.Lexer
import org.Linter
import org.Parser
import java.io.File

class AnalyzingCommand(
    private val input: String,
    private val lexer: Lexer,
    private val parser: Parser
) : Command {
    override fun execute() {
        // analyze file.txt file.json
        val parts = input.split(" ")
        val file = parts[1]
        val jsonFilePath = parts[2]

        val tokens = lexer.tokenize(file)
        val ast = parser.parse(tokens)

        // Leo el json file y lo parseo a un JsonObject
        val jsonContent = File(jsonFilePath).readText()
        val jsonObject = Json.parseToJsonElement(jsonContent).jsonObject

        val linter = Linter(jsonObject)
        val lintedOutput = linter.lint(ast)

        val warnings = lintedOutput.getList()
        warnings.forEach { println(it) } // printeo en consola los warnings.
    }
}
