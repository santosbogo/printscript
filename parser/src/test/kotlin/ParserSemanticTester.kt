import org.Lexer
import org.LexiconFactory
import org.Parser
import org.junit.jupiter.api.Test

import java.io.File

class ParserSemanticTester {

    @Test
    fun testSingleFile() {
        val lexer = Lexer(LexiconFactory().createDefaultLexicon())
        val parser = Parser()
        val reader = TestReader()
        val file = File("src/test/resources/parser-examples/semanticerrortest.txt")
        val (code, nodes, shouldSucceed) = reader.readTokens(file.path)
        val tokens = lexer.tokenize(code)

        try {
            val ast = parser.parse(tokens)
            if (!shouldSucceed) {
                assert(false) { "Expected an error but test passed for file ${file.name}" }
            }
        } catch (e: Exception) {
            if (shouldSucceed) {
                assert(false) { "Unexpected error in file ${file.name}: ${e.message}" }
            }
        }
    }
}
