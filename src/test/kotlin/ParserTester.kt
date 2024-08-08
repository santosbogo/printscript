import org.Location
import org.Token
import org.astnode.ASTNode
import org.junit.jupiter.api.Test
import org.lexer.Lexer
import org.lexer.LexiconFactory
import org.parser.Parser
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
            val nodes: List<ASTNode> = parser.parse(tokens)
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

            //cuando falla, me dice donde falló.
            for (i in ast.indices) {
                assert(ast[i].type == nodes[i]) {
                    "Mismatch in file ${file.name} at ${ast[i].location}: expected ${nodes[i]}, found ${ast[i].type}"
                }
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
