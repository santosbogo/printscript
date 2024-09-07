package org

import kotlinx.serialization.json.JsonObject
import org.astnode.ProgramNode
import org.junit.jupiter.api.Test
import java.io.File

class FormatterTester {
    private fun compareResults(
        node: ProgramNode,
        formater: Formatter,
        shouldSucceed: Boolean,
        file: File,
        solution: List<String>,
        json: JsonObject
    ) {
        try {
            val rules = RulesFactory().getRules(json.toString(), "1.1")
            val result = formater.format(node, rules).toString().split("\n")
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
        val examplesDir = File("src/test/resources/examples-v10/formatter")
        val reader = TestReader()
        val lexer = LexerFactory.createLexerV11()
        val parser = ParserFactory.createParserV11()

        examplesDir.listFiles { file -> file.isFile && file.extension == "txt" }?.forEach { file ->
            val (code, solution, shouldSucceed, json) = reader.readTokens(file.path)
            val lexerResult = lexer.tokenize(code)
            val parserResult = parser.parse(lexerResult.tokens)
            val programNode = parserResult.programNode!!
            val formater = Formatter()
            compareResults(programNode, formater, shouldSucceed, file, solution, json)
        }
    }

    @Test
    fun testSingleFile() {
        val file = File("src/test/resources/examples-v10/formatter/if-brace-below-line.txt")

        val reader = TestReader()
        val (code, solution, shouldSucceed, json) = reader.readTokens(file.path)

        val lexer = LexerFactory.createLexerV11()
        val lexerResult = lexer.tokenize(code)

        val parser = ParserFactory.createParserV11()
        val parserResult = parser.parse(lexerResult.tokens)
        val programNode = parserResult.programNode!!
        val formatter = Formatter()

        compareResults(programNode, formatter, shouldSucceed, file, solution, json)
    }
}
