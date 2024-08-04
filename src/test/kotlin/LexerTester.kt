import org.junit.jupiter.api.Test
import org.lexer.Lexer
import java.io.File

class LexerTester {

    @Test
    fun testFile() {
        val lexer = Lexer()
        val reader = TestReader()
        val (code, solution) = reader.readTokens("src/test/resources/examples/variabledeclaration.txt")
        val tokens = lexer.tokenize(code)

        for (i in tokens.indices) {
            assert(tokens[i].type == solution[i])
        }
    }
}