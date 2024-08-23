import org.Lexer
import org.Linter
import org.LinterFactory
import org.Parser
import org.astnode.ProgramNode
import org.junit.jupiter.api.Test
import java.io.File

class LinterTester {
    @Test
    fun testSingleWarning() {
        val file = File("src/test/resources/examples/defaultExample.txt")

        // me devuelve el codigo que entra, la warning q tiene q devolver, y si debería funcionar.
        val reader = TestReader()
        val (code, expectedWarning, shouldSucceed) = reader.readTokens(file.path)

        // meto el codigo en el lexer, obtengo tokens
        val lexer = Lexer()
        val lexerResult = lexer.tokenize(code)

        // meto tokens en el parser, obtengo los nodos.
        val parser = Parser()
        val parserResult = parser.parse(lexerResult.tokens)
        val programNode = parserResult.programNode!!

        val linterFactory = LinterFactory()
        val linter = linterFactory.createDefaultLinter("src/test/kotlin/org/jsons/defaultJson.json")

        // compare the linter output with the expectedWarning.
        compareResults(linter, code, programNode, expectedWarning, shouldSucceed)
    }

    private fun compareResults(
        linter: Linter,
        code: String,
        programNode: ProgramNode,
        expectedWarnings: List<String>,
        shouldSucceed: Boolean
    ) {
        val reportList = linter.lint(programNode).getList()
        if (!shouldSucceed) {
            assert(false) { "Expected an error but test passed for code $code" }
        }

        assert(reportList.firstOrNull() == expectedWarnings.firstOrNull()) {
            "Mismatch in code \"$code\": expected \"${expectedWarnings.firstOrNull()}\", " +
                "found \"${reportList.firstOrNull()}\""
        }
    }
}
