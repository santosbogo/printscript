import org.junit.jupiter.api.Test
import org.lexer.Lexer
import org.lexer.LexiconFactory
import java.io.File

class LexerTester {

    @Test
    fun testFile() {
        val lexiconFactory = LexiconFactory()
        val lexer = Lexer(lexiconFactory.createDefaultLexicon())
        val reader = TestReader()
        val (code, solution) = reader.readTokens("src/test/resources/examples/multiplevariabledeclaration.txt")
        val tokens = lexer.tokenize(code)

        for (i in tokens.indices) {
            assert(tokens[i].type == solution[i])
        }
    }
}