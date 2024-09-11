package org

import TestReader
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.junit.jupiter.api.Test
import java.io.File
import java.io.StringReader

class LinterTesterV10 {
    @Test
    fun testSingleWarning() {
        val file = File("src/test/resources/examples-v10/printUseExample.txt")

        val reader = TestReader()
        val (code, expectedWarning, shouldSucceed) = reader.readTokens(file.path)

        val lexer = Lexer(LexiconFactory().createLexiconV10(), StringReader(code))
        val parser = ParserFactory.createParserV10(lexer)
        val linter = LinterFactory().createLinterV10(parser)

        val jsonContent = File("src/test/kotlin/org/jsons/jsonV10.json").readText()
        val jsonObject = Json.parseToJsonElement(jsonContent).jsonObject

        compareResults(linter, code, expectedWarning, shouldSucceed, jsonObject)
    }

    @Test
    fun testMultipleWarnings() {
        val dir = File("src/test/resources/examples-v10")

        dir.listFiles {
            file ->
            file.isFile && file.extension == "txt"
        }?.forEach {
            file ->
            val reader = TestReader()
            val (code, expectedWarnings, shouldSucceed) = reader.readTokens(file.path)

            val lexer = Lexer(LexiconFactory().createLexiconV10(), StringReader(code))
            val parser = ParserFactory.createParserV10(lexer)
            val linter = LinterFactory().createLinterV10(parser)

            val jsonContent = File("src/test/kotlin/org/jsons/jsonV10.json").readText()
            val jsonObject = Json.parseToJsonElement(jsonContent).jsonObject

            compareResults(linter, code, expectedWarnings, shouldSucceed, jsonObject)
        }
    }

    private fun compareResults(
        linter: Linter,
        code: String,
        expectedWarnings: List<String>,
        shouldSucceed: Boolean,
        jsonFile: JsonObject
    ) {
        val reportList = linter.lint(jsonFile).getList()
        if (!shouldSucceed) {
            assert(false) { "Expected an error but test passed for code $code" }
        }

        expectedWarnings.forEachIndexed {
            index, expectedWarning ->
            assert(reportList[index] == expectedWarning) {
                "Mismatch in code \"$code\": expected \"$expectedWarning\", found \"${reportList[index]}\""
            }
        }
    }
}
