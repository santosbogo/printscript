package org

import org.junit.jupiter.api.Test
import java.io.File

class ParserTesterV11 {
    @Test
    fun testSingleFile() {
        val file = File("src/test/resources/examples-v11/simpleReadInput.txt")

        val lexer = Lexer(LexiconFactory().createLexiconV11())
        val parser = ParserFactory.createParserV11()
        val reader = TestReader()

        val (code, solution, shouldSucceed) = reader.readTokens(file.path)
        val lexerResult = lexer.tokenize(code)
        val parserResult = parser.parse(lexerResult.tokens)

        try {
            val nodes = parserResult.programNode!!.statements
            if (!shouldSucceed) {
                assert(false) { "Expected an error but test passed for file ${file.name}" }
            }
            for (i in nodes.indices) {
                assert(nodes[i].type == solution[i]) {
                    "Mismatch in file ${file.name} at ${nodes[i].location}: " +
                        "expected ${solution[i]}, found ${nodes[i].type}"
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
        val reader = TestReader()
        val examplesDir = File("src/test/resources/examples-v11")

        val lexer = Lexer(LexiconFactory().createLexiconV11()) // uso nueva version del lexicon.

        examplesDir.listFiles { file -> file.isFile && file.extension == "txt" }?.forEach { file ->
            val (code, solution, shouldSucceed) = reader.readTokens(file.path)
            val lexerResult = lexer.tokenize(code)

            val parser = ParserFactory.createParserV11()
            val parserResult = parser.parse(lexerResult.tokens)
            try {
                val nodes = parserResult.programNode!!.statements
                if (!shouldSucceed) {
                    assert(false) { "Expected an error but test passed for file ${file.name}" }
                }
                for (i in nodes.indices) {
                    assert(nodes[i].type == solution[i]) {
                        "Mismatch in file ${file.name} at ${nodes[i].location}: " +
                            "expected ${solution[i]}, found ${nodes[i].type}"
                    }
                }
            } catch (e: Exception) {
                if (shouldSucceed) {
                    assert(false) { "Unexpected error in file ${file.name}: ${e.message}" }
                }
            }
        }
    }
}
