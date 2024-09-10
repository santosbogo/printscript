package org

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import java.io.File
import org.junit.jupiter.api.Test
import java.io.StringReader

class FormatterTesterV11 {
    private fun getJsonFromFile(): JsonObject {
        val jsonContent = File("src/test/resources/rulesExample.json").readText()
        return Json.parseToJsonElement(jsonContent).jsonObject
    }

    private fun compareResults(
        formater: Formatter,
        shouldSucceed: Boolean,
        file: File,
        solution: List<String>,
        json: JsonObject = getJsonFromFile()
    ) {
        try {
            val rules = RulesFactory().getRules(json.toString(), "1.1")
            val result = formater.format(rules).toString().split("\n")
            if (!shouldSucceed) {
                assert(false) { "Expected an error but test passed for file ${file.name}" }
            }

            for (i in solution.indices) {
                assert(result[i] == solution[i]) {
                    "Mismatch in file \"${file.name}\" at " +
                        "line ${i + 1}: expected \"${solution[i]}\", found \"${result[i]}\""
                }
            }
        } catch (e: Exception) {
            if (shouldSucceed) {
                assert(false) { "Unexpected error in file ${file.name}: ${e.message}" }
            }
        }
    }

    @Test
    fun testFiles() {
        val examplesDir = File("src/test/resources/examples-v11/")
        val reader = TestReader()

        examplesDir.listFiles { file -> file.isFile && file.extension == "txt" }?.forEach { file ->
            val (code, solution, shouldSucceed, json) = reader.readTokens(file.path)
            val lexer = LexerFactory.createLexerV11(StringReader(code))
            val parser = ParserFactory.createParserV11(lexer)

            val formatter = Formatter(parser)
            compareResults(formatter, shouldSucceed, file, solution, json)
        }
    }

    @Test
    fun testSingleFile() {
        val file = File("src/test/resources/examples-v11/ifelsestatement.txt")

        val reader = TestReader()
        val (code, solution, shouldSucceed, json) = reader.readTokens(file.path)
        val lexer = LexerFactory.createLexerV11(StringReader(code))
        val parser = ParserFactory.createParserV11(lexer)
        val formatter = Formatter(parser)

        compareResults(formatter, shouldSucceed, file, solution, json)
    }
}
