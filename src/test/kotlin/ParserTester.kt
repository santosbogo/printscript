import org.Location
import org.junit.jupiter.api.Test
import org.lexer.Token
import org.parser.Parser
import org.parser.astnode.ASTNode
import java.io.File

class ParserTester {

    @Test
    fun testSingleFile() {
        val parser = Parser()
        val reader = TestReader()
        val file = File("src/test/resources/parser-examples/variabledeclarationtest")
        val (code, solution, shouldSucceed) = reader.readTokens(file.path)
        val tokens : List<Token> = stringToTokens(code)
        try {
            val nodes : List<ASTNode> = parser.parse(tokens)
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

    private fun stringToTokens(code: String): List<Token> {
        val list: List<String> = code.replace(" ", "").split(",")
        return list.map { Token(it, "", Location(0,0)) }
    }
}