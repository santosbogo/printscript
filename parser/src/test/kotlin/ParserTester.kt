import org.Lexer
import org.Parser
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertFailsWith

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
        val lexer = Lexer()
        val parser = Parser()
        val reader = TestReader()
        val file = File("src/test/resources/examples/variabledeclarationwithoperation.txt")
        val (code, solution, shouldSucceed) = reader.readTokens(file.path)
        val tokens = lexer.tokenize(code)
        val nodes = parser.parse(tokens).statements
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

    @Test
    fun testNoMatchingFormula() {
        val lexer = Lexer()
        val parser = Parser()
        val tokens = lexer.tokenize("let let a: number = 10;")

        // Expecting an exception to be thrown
        val exception = assertFailsWith<Exception> {
            parser.parse(tokens)
        }

        // Optionally, you can assert that the exception message matches your expectation
        assert(exception.message?.contains("Syntax error: Invalid statement") == true)
    }

    @Test
    fun testMissingSemicolon() {
        val lexer = Lexer()
        val parser = Parser()
        val tokens = lexer.tokenize("let a: number = 10; a = 5")

        // Expecting an exception to be thrown
        val exception = assertFailsWith<Exception> {
            parser.parse(tokens)
        }

        // Optionally, you can assert that the exception message matches your expectation
        assert(
            exception.message?.contains(
                "Unexpected end of input. Missing semicolon at the end of the file."
            ) == true
        )
    }
}
