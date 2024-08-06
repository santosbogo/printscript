import org.junit.jupiter.api.Test
import org.lexer.Lexer
import org.lexer.LexiconFactory
import java.io.File

class LexerTester {

    @Test
    fun testFiles() {
        val lexiconFactory = LexiconFactory()
        val lexer = Lexer(lexiconFactory.createDefaultLexicon())
        val reader = TestReader()
        val examplesDir = File("src/test/resources/lexer-examples")

        examplesDir.listFiles { file -> file.isFile && file.extension == "txt" }?.forEach { file ->
            val (code, solution, shouldSucceed) = reader.readTokens(file.path)
            try {
                val tokens = lexer.tokenize(code)
                if (!shouldSucceed) {
                    assert(false) { "Expected an error but test passed for file ${file.name}" }
                }
                for (i in tokens.indices) {
                    assert(tokens[i].type == solution[i]) {
                        "Mismatch in file ${file.name} at ${tokens[i].location}: expected ${solution[i]}, found ${tokens[i].type}"
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
        val lexiconFactory = LexiconFactory()
        val lexer = Lexer(lexiconFactory.createDefaultLexicon())
        val reader = TestReader()
        val file = File("src/test/resources/lexer-examples/unrecognizedtoken.txt")
        val (code, solution, shouldSucceed) = reader.readTokens(file.path)
        try {
            val tokens = lexer.tokenize(code)
            if (!shouldSucceed) {
                assert(false) { "Expected an error but test passed for file ${file.name}" }
            }
            for (i in tokens.indices) {
                assert(tokens[i].type == solution[i]) {
                    "Mismatch in file ${file.name} at ${tokens[i].location}: expected ${solution[i]}, found ${tokens[i].type}"
                }
            }
        } catch (e: Exception) {
            if (shouldSucceed) {
                assert(false) { "Unexpected error in file ${file.name}: ${e.message}" }
            }
        }
    }
}