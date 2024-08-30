package org.v11

import TestReader
import org.Lexer
import org.LexiconFactory
import org.ParserFactory
import org.junit.jupiter.api.Test
import java.io.File

class ParserTester {
    @Test
    fun testFiles() {
        val reader = TestReader()
        val examplesDir = File("src/test/resources/examples11")

        val lexer = Lexer(LexiconFactory().createLexiconV11()) // uso nueva version del lexicon.

        examplesDir.listFiles { file -> file.isFile && file.extension == "txt" }?.forEach { file ->
            val (code, solution, shouldSucceed) = reader.readTokens(file.path)
            val lexerResult = lexer.tokenize(code)

            val parserFactory = ParserFactory()
            val parser = parserFactory.createParserV11()
            val parserResult = parser.parse(lexerResult.tokens)
            val nodes = parserResult.programNode!!.statements
            try {
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
