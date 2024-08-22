import org.Lexer
import org.Location
import org.Token
import org.junit.jupiter.api.Test
import java.io.File

class LexerTester {

    @Test
    fun testFiles() {
        val lexer = Lexer()
        val reader = TestReader()
        val examplesDir = File("src/test/resources/examples")

        examplesDir.listFiles { file -> file.isFile && file.extension == "txt" }?.forEach { file ->
            val (code, solution, shouldSucceed) = reader.readTokens(file.path)
            try {
                val tokens = lexer.tokenize(code)
                if (!shouldSucceed) {
                    assert(false) { "Expected an error but test passed for file ${file.name}" }
                }
                for (i in tokens.indices) {
                    assert(tokens[i].type == solution[i]) {
                        "Mismatch in file ${file.name} at ${tokens[i].location}: " +
                            "expected ${solution[i]}, found ${tokens[i].type}"
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
        val reader = TestReader()
        val file = File("src/test/resources/examples/unrecognizedtoken.txt")
        val (code, solution, shouldSucceed) = reader.readTokens(file.path)
        try {
            val tokens = lexer.tokenize(code)
            if (!shouldSucceed) {
                assert(false) { "Expected an error but test passed for file ${file.name}" }
            }
            for (i in tokens.indices) {
                assert(tokens[i].type == solution[i]) {
                    "Mismatch in file ${file.name} at ${tokens[i].location}: " +
                        "expected ${solution[i]}, found ${tokens[i].type}"
                }
            }
        } catch (e: Exception) {
            if (shouldSucceed) {
                assert(false) { "Unexpected error in file ${file.name}: ${e.message}" }
            }
        }
    }

    @Test
    fun testToStringToken() {
        val token = Token("ToStringToken", "value", Location(1, 1))
        println(token.toString())
    }
}
