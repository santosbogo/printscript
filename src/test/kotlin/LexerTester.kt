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
            val (code, solution) = reader.readTokens(file.path)
            val tokens = lexer.tokenize(code)

            for (i in tokens.indices) {
                assert(tokens[i].type == solution[i]) {
                    "Mismatch in file ${file.name} at ${tokens[i].location}: expected ${solution[i]}, found ${tokens[i].type}"
                }
            }
        }
    }

    @Test
    fun testSingleFile() {
        val lexiconFactory = LexiconFactory()
        val lexer = Lexer(lexiconFactory.createDefaultLexicon())
        val reader = TestReader()
        val file = File("src/test/resources/lexer-examples/variabledeclaration.txt")
        val (code, solution) = reader.readTokens(file.path)
        val tokens = lexer.tokenize(code)

        for (i in tokens.indices) {
            assert(tokens[i].type == solution[i]) {
                "Mismatch in file ${file.name} at ${tokens[i].location}: expected ${solution[i]}, found ${tokens[i].type}"
            }
        }
    }
}