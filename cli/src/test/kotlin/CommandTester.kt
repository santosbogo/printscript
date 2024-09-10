import com.github.ajalt.clikt.testing.test
import org.Analyze
import org.Execute
import org.Format
import org.Validate
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

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
        val exception = assertFailsWith<Exception> {
            command.test("src/test/resources/examples-v10/failingLexer.txt")
        }
        assert(exception.message?.contains("Lexicon Error: No token found for component") == true)
    }

    @Test
    fun testExecuteFailingParser() {
        val command = Execute()

        val exception = assertFailsWith<Exception> {
            command.test("src/test/resources/examples-v10/failingParser.txt")
        }
        assert(exception.message?.contains("no es del tipo") == true)
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
        val exception = assertFailsWith<Exception> {
            command.test(
                "src/test/resources/examples-v10/failingLexer.txt " +
                    "src/test/resources/analyzeJsons/defaultJson.json"
            )
        }
        assert(exception.message?.contains("No token found for component") == true)
    }

    @Test
    fun testAnalyzerFailingParser() {
        val command = Analyze()
        val exception = assertFailsWith<Exception> {
            command.test(
                "src/test/resources/examples-v10/failingParser.txt " +
                    "src/test/resources/analyzeJsons/defaultJson.json"
            )
        }
        assert(exception.message?.contains("no es del tipo") == true)
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
        val exception = assertFailsWith<Exception> {
            command.test("src/test/resources/examples-v10/failingLexer.txt")
        }
        assert(exception.message?.contains("Lexicon Error: No token found for component") == true)
    }

    @Test
    fun testValidateFailingParser() {
        val command = Validate()
        val exception = assertFailsWith<Exception> {
            command.test("src/test/resources/examples-v10/failingParser.txt")
        }
        assert(exception.message?.contains("Semantic error: Variable a no es del tipo string") == true)
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
        val exception = assertFailsWith<Exception> {
            command.test(
                "src/test/resources/examples-v10/failingLexer.txt " +
                    "src/test/resources/formatJsons/rulesExample.json"
            )
        }
        assert(exception.message?.contains("Lexicon Error: No token found for component") == true)
    }

    @Test
    fun testFormatFailingParser() {
        val command = Format()
        val exception = assertFailsWith<Exception> {
            command.test(
                "src/test/resources/examples-v10/failingParser.txt " +
                    "src/test/resources/formatJsons/rulesExample.json"
            )
        }
        assert(exception.message?.contains("Semantic error: Variable a no es del tipo string") == true)
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
