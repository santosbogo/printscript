package test.kotlin

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.Formatter
import org.Lexer
import org.Location
import org.Parser
import org.RulesFactory
import org.astnode.ProgramNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.statementnode.VariableDeclarationNode
import org.junit.jupiter.api.Test
import java.io.File

class FormatterTester {

    private fun getJsonFromFile(): JsonObject {
        val jsonContent = File("src/main/kotlin/rulesExample.json").readText()
        return Json.parseToJsonElement(jsonContent).jsonObject
    }

    private fun compareResults(
        formater: Formatter,
        shouldSucceed: Boolean,
        file: File,
        solution: List<String>
    ) {
        try {
            val result = formater.format().split("\n")
            if (!shouldSucceed) {
                assert(false) { "Expected an error but test passed for file ${file.name}" }
            }

            for (i in solution.indices) {
                assert(result[i] == solution[i]) {
                    "Mismatch in file \"${file.name}\" at line ${i + 1}: expected \"${solution[i]}\", found \"${result[i]}\""
                }
            }

        } catch (e: Exception) {
            if (shouldSucceed) {
                assert(false) { "Unexpected error in file ${file.name}: ${e.message}" }
            }
        }
    }

    @Test
    fun testFiles() {
        val examplesDir = File("src/test/resources/examples")
        val reader = TestReader()
        val lexer = Lexer()
        val parser = Parser()

        val json = getJsonFromFile()

        examplesDir.listFiles { file -> file.isFile && file.extension == "txt" }?.forEach { file ->
            val (code, solution, shouldSucceed) = reader.readTokens(file.path)
            val tokens = lexer.tokenize(code)
            val nodes = parser.parse(tokens)
            val formater = Formatter(nodes, json);

            compareResults(formater, shouldSucceed, file, solution)
        }
    }

    @Test
    fun testSingleFile() {
        val file = File("src/test/resources/examples/manylinebreaks.txt")

        val reader = TestReader()
        val (code, solution, shouldSucceed) = reader.readTokens(file.path)

        val lexer = Lexer()
        val tokens = lexer.tokenize(code)

        val parser = Parser()
        val nodes = parser.parse(tokens)

        val formater = Formatter(nodes, getJsonFromFile() );

        compareResults(formater, shouldSucceed, file, solution)
    }

    @Test
    fun testFormat() {
        val variableDeclarationNode = VariableDeclarationNode(
            "VariableDeclarationNode",
            Location(1, 1),
            IdentifierNode("IdentifierNode", Location(1, 1), "a", "number"),
            LiteralNode("Literal", Location(1, 17), LiteralValue.NumberValue(10)),
            "let"
        )

        val programNode = ProgramNode("ProgramNode", Location(1, 1), listOf(variableDeclarationNode))

        // Get JSON from file
        val filePath = "src/main/kotlin/rulesExample.json"
        val jsonContent = File(filePath).readText()
        val json = Json.parseToJsonElement(jsonContent).jsonObject

        val formatter = Formatter(programNode, json)
        println(formatter.format())
    }

    @Test // Obtiene las reglas desde un archivo JSON
    fun getRulesFromJson() {
        val rulesFactory = RulesFactory()
        val filePath = "src/main/kotlin/rulesExample.json"
        val jsonContent = File(filePath).readText()
        println(jsonContent)
        rulesFactory.createRules(Json.parseToJsonElement(jsonContent).jsonObject)
    }

    @Test
    fun testWholeProgram() {
        val lexer = Lexer()
        val parser = Parser()
        val input = "let b: number = 10;b = 5;println(4);let a: string = 'hola';println(a);println(1 + 4);println(a + b);"

        val tokens = lexer.tokenize(input)
        val programNode = parser.parse(tokens)
        // Get JSON from file
        val filePath = "src/main/kotlin/rulesExample.json"
        val jsonContent = File(filePath).readText()
        val json = Json.parseToJsonElement(jsonContent).jsonObject

        val formatter = Formatter(programNode, json)
        println(formatter.format())
    }
}
