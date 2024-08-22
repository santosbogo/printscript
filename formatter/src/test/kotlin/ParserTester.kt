package test.kotlin

import org.Lexer
import org.Parser
import org.junit.jupiter.api.Test
import java.io.File

class ParserTester {

    @Test
    fun testFiles() {
        val lexer = Lexer()
        val parser = Parser()
        val reader = TestReader()
        val examplesDir = File("src/test/resources/examples")

        examplesDir.listFiles { file -> file.isFile && file.extension == "txt" }?.forEach { file ->
            val (code, solution, shouldSucceed) = reader.readTokens(file.path)
            val tokens = lexer.tokenize(code)
            val nodes = parser.parse(tokens).statements
            try {
                if (!shouldSucceed) {
                    assert(false) { "Expected an error but test passed for file ${file.name}" }
                }
                for (i in nodes.indices) {
                    assert(nodes[i].type == solution[i]) {
                        "Mismatch in file ${file.name} at ${nodes[i].location}: expected ${solution[i]}, found ${nodes[i].type}"
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
        val lexer = Lexer()
        val parser = Parser()
        val reader = TestReader()
        val file = File("src/test/resources/examples/variabledeclarationwithoperation.txt")
        val (code, solution, shouldSucceed) = reader.readTokens(file.path)
        val tokens = lexer.tokenize(code)

        try {
            val nodes = parser.parse(tokens).statements
            if (!shouldSucceed) {
                assert(false) { "Expected an error but test passed for file ${file.name}" }
            }
            for (i in nodes.indices) {
                assert(nodes[i].type == solution[i]) {
                    "Mismatch in file ${file.name} at ${nodes[i].location}: expected ${solution[i]}, found ${nodes[i].type}"
                }
            }
        } catch (e: Exception) {
            if (shouldSucceed) {
                assert(false) { "Unexpected error in file ${file.name}: ${e.message}" }
            }
        }
    }
}
