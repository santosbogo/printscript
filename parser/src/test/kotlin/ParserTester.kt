import org.Lexer
import org.LexiconFactory
import org.Parser
import org.junit.jupiter.api.Test
import org.common.Location
import org.common.Token
import org.common.astnode.ProgramNode
import java.io.File

class ParserTester {

    @Test
    fun testSingleFile() {
        val parser = Parser()
        val reader = TestReader()
        val file = File("src/test/resources/parser-examples/variabledeclarationtest")
        val (code, solution, shouldSucceed) = reader.readTokens(file.path)
        val tokens: List<Token> = stringToTokens(code)
        try {
            val nodes: ProgramNode = parser.parse(tokens)
            if (!shouldSucceed) {
                assert(false) { "Expected an error but test passed for file ${file.name}" }
            }

        } catch (e: Exception) {
            if (shouldSucceed) {
                assert(false) { "Unexpected error in file ${file.name}: ${e.message}" }
            }
        }
    }

    @Test
    fun semanticTest() {
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

    private fun stringToTokens(code: String): List<Token> {
        val list: List<String> = code.replace(" ", "").split(",")
        return list.map { Token(it, "", Location(0, 0)) }
    }
}
