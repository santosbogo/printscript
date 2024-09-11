package org

import org.junit.jupiter.api.Test
import java.io.File
import java.io.StringReader

class ParserTesterV11 {
    @Test
    fun testFiles() {
        val reader = TestReader()
        val examplesDir = File("src/test/resources/examples-v11")

        examplesDir.listFiles { file -> file.isFile && file.extension == "txt" }?.forEach { file ->
            val (code, solution, shouldSucceed) = reader.readTokens(file.path)
            val lexer = Lexer(LexiconFactory().createLexiconV11(), StringReader(code))
            val parser = ParserFactory.createParserV11(lexer)
            try {
                val nodes = parser.collectAllASTNodes()
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

    @Test
    fun testSingleFile() {
        val file = File("src/test/resources/examples-v11/ifWithIdentifier.txt")
        val reader = TestReader()
        val (code, solution, shouldSucceed) = reader.readTokens(file.path)
        val lexer = Lexer(LexiconFactory().createLexiconV11(), StringReader(code))
        val parser = ParserFactory.createParserV11(lexer)

        try {
            val nodes = parser.collectAllASTNodes()
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
