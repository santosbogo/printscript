import com.github.ajalt.clikt.testing.test
import org.Analyze
import org.Execute
import org.Format
import org.Validate
import org.junit.jupiter.api.Test

class CommandTester {
    @Test
    fun testExecute() {
        val command = Execute()
        val result = command.test("src/test/resources/examples-v10/test1.txt")
        assert(
            result.stdout == "Lexing...\n" +
                "\n" +
                "Parsing...\n" +
                "\n" +
                "Executing...\n" +
                "\n" +
                "Execution successful\n"
        )
        assert(result.statusCode == 0)
    }

    @Test
    fun testExecuteFailingLexer() {
        val command = Execute()
        val result = command.test("src/test/resources/examples-v10/failingLexer.txt")
        assert(result.stderr == "Lexicon Error: No token found for component: & at (line: 1, column: 17)\n")
    }

    @Test
    fun testExecuteFailingParser() {
        val command = Execute()
        val result = command.test("src/test/resources/examples-v10/failingParser.txt")
        assert(
            result.stderr == "Semantic error: Variable a no es del tipo string" +
                " y no puede ser asignada con un valor de tipo number\n"
        )
    }

    @Test
    fun testAnalyzer() {
        val command = Analyze()
        val result = command.test(
            "src/test/resources/examples-v10/test1.txt " +
                "src/test/resources/analyzeJsons/defaultJson.json"
        )
        assert(
            result.stdout == "Lexing...\n" +
                "\n" +
                "Parsing...\n" +
                "\n" +
                "Analyzing...\n" +
                "\n" +
                "Analyze successful\n"
        )
        assert(result.statusCode == 0)
    }

    @Test
    fun testAnalyzerFailingLexer() {
        val command = Analyze()
        val result = command.test(
            "src/test/resources/examples-v10/failingLexer.txt " +
                "src/test/resources/analyzeJsons/defaultJson.json"
        )
        assert(result.stderr == "Lexicon Error: No token found for component: & at (line: 1, column: 17)\n")
    }

    @Test
    fun testAnalyzerFailingParser() {
        val command = Analyze()
        val result = command.test(
            "src/test/resources/examples-v10/failingParser.txt " +
                "src/test/resources/analyzeJsons/defaultJson.json"
        )
        assert(
            result.stderr == "Semantic error: Variable a no es del tipo string" +
                " y no puede ser asignada con un valor de tipo number\n"
        )
    }

    @Test
    fun testValidate() {
        val command = Validate()
        val result = command.test("src/test/resources/examples-v10/test1.txt")
        assert(
            result.stdout == "Lexing...\n" +
                "\n" +
                "Parsing...\n" +
                "\n" +
                "Validation successful\n"
        )
        assert(result.statusCode == 0)
    }

    @Test
    fun testValidateFailingLexer() {
        val command = Validate()
        val result = command.test("src/test/resources/examples-v10/failingLexer.txt")
        assert(result.stderr == "Lexicon Error: No token found for component: & at (line: 1, column: 17)\n")
    }

    @Test
    fun testValidateFailingParser() {
        val command = Validate()
        val result = command.test("src/test/resources/examples-v10/failingParser.txt")
        assert(
            result.stderr == "Semantic error: Variable a no es del tipo string" +
                " y no puede ser asignada con un valor de tipo number\n"
        )
    }

    @Test
    fun testFormat() {
        val command = Format()
        val result = command.test(
            "src/test/resources/examples-v10/test1.txt " +
                "src/test/resources/formatJsons/rulesExample.json"
        )
        assert(
            result.stdout == "Lexing...\n" +
                "\n" +
                "Parsing...\n" +
                "\n" +
                "Formatting...\n" +
                "\n" +
                "Format successful\n"
        )
        assert(result.statusCode == 0)
    }

    @Test
    fun testFormatFailingLexer() {
        val command = Format()
        val result = command.test(
            "src/test/resources/examples-v10/failingLexer.txt " +
                "src/test/resources/formatJsons/rulesExample.json"
        )
        assert(result.stderr == "Lexicon Error: No token found for component: & at (line: 1, column: 17)\n")
    }

    @Test
    fun testFormatFailingParser() {
        val command = Format()
        val result = command.test(
            "src/test/resources/examples-v10/failingParser.txt " +
                "src/test/resources/formatJsons/rulesExample.json"
        )
        assert(
            result.stderr == "Semantic error: Variable a no es del tipo string" +
                " y no puede ser asignada con un valor de tipo number\n"
        )
    }

    @Test
    fun testFile2Analyze() {
        val command = Analyze()
        val result = command.test(
            "src/test/resources/examples-v10/test2.txt " +
                "src/test/resources/analyzeJsons/defaultJson.json"
        )
        assert(result.statusCode == 0)
    }
}
