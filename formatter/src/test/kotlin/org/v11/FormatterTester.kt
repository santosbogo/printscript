package test.kotlin.org.v11

import java.io.File
import org.Formatter
import org.Lexer
import org.LexiconFactory
import org.ParserFactory
import org.RulesFactory
import org.junit.jupiter.api.Test
import test.kotlin.TestReader

class FormatterTester {

    private fun compareResults(
        formater: Formatter,
        shouldSucceed: Boolean,
        file: File,
        solution: List<String>
    ) {
        try {
            val result = formater.format().toString().split("\n")
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
        val lexer = Lexer(LexiconFactory().createLexiconV11())
        val parser = ParserFactory().createParserV11()

        examplesDir.listFiles { file -> file.isFile && file.extension == "txt" }?.forEach { file ->
            val (code, solution, shouldSucceed, json) = reader.readTokens(file.path)
            val lexerResult = lexer.tokenize(code)
            val parserResult = parser.parse(lexerResult.tokens)
            val programNode = parserResult.programNode!!
            val formater = Formatter(programNode, json, RulesFactory().createRulesForV11(json))
            compareResults(formater, shouldSucceed, file, solution)
        }
    }

    @Test
    fun testSingleFile() {
        val file = File("src/test/resources/examples-v11/ifelsestatement.txt")

        val reader = TestReader()
        val (code, solution, shouldSucceed, json) = reader.readTokens(file.path)

        val lexer = Lexer(LexiconFactory().createLexiconV11())
        val lexerResult = lexer.tokenize(code)

        val parser = ParserFactory().createParserV11()
        val parserResult = parser.parse(lexerResult.tokens)
        val programNode = parserResult.programNode!!

        val formater = Formatter(programNode, json, RulesFactory().createRulesForV11(json))
        compareResults(formater, shouldSucceed, file, solution)
    }
}
