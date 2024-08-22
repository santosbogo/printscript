package test.kotlin

import java.io.File
import kotlin.test.assertFailsWith
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.*
import org.astnode.ProgramNode
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.statementnode.VariableDeclarationNode
import org.junit.jupiter.api.Test

class FormatterTester {

    private fun getJsonFromFile(): JsonObject {
        val jsonContent = File("src/test/resources/rulesExample.json").readText()
        return Json.parseToJsonElement(jsonContent).jsonObject
    }

    private fun compareResults(
        formatter: Formatter,
        shouldSucceed: Boolean,
        file: File,
        solution: List<String>
    ) {
        try {
            val result = formatter.format().toString().split("\n")
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

        examplesDir.listFiles { file -> file.isFile && file.extension == "txt" }?.forEach { file ->
            val (code, solution, shouldSucceed, json) = reader.readTokens(file.path)
            val tokens = lexer.tokenize(code)
            val nodes = parser.parse(tokens)
            val formatter = Formatter(nodes, json)
            compareResults(formatter, shouldSucceed, file, solution)
        }
    }

    @Test
    fun testSingleFile() {
        val file = File("src/test/resources/examples/doubleprintln.txt")

        val reader = TestReader()
        val (code, solution, shouldSucceed, json) = reader.readTokens(file.path)

        val lexer = Lexer()
        val tokens = lexer.tokenize(code)

        val parser = Parser()
        val nodes = parser.parse(tokens)

        val formatter = Formatter(nodes, json)

        compareResults(formatter, shouldSucceed, file, solution)
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

        val json = getJsonFromFile()

        val formatter = Formatter(programNode, json)
        println(formatter.format())
    }

    @Test
    fun getRulesFromJson() {
        val rulesFactory = RulesFactory()
        val jsonContent = getJsonFromFile().jsonObject.toString()
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
        val json = getJsonFromFile()

        val formatter = Formatter(programNode, json)
        println(formatter.format())
    }

    @Test
    fun testUnreachedCases() {
        val formatterVisitor = FormatterVisitor()
        val literalNode = LiteralNode("LiteralNode", Location(1, 1), LiteralValue.NumberValue(5))
        val binaryExpressionNode = BinaryExpressionNode("BinaryExpressionNode", Location(1, 1), literalNode, literalNode, "+")
        val identifierNode = IdentifierNode("IdentifierNode", Location(1, 1), "x", "Number")
        val programNode = ProgramNode("ProgramNode", Location(1, 1), listOf(binaryExpressionNode))
        val nodes = listOf(programNode, binaryExpressionNode, identifierNode, literalNode)
        for (node in nodes) {
            node.accept(formatterVisitor)
        }

        val jsonContent = File("src/test/resources/corruptExample.json").readText()
        val json = Json.parseToJsonElement(jsonContent).jsonObject
        assertFailsWith<IllegalStateException>("Rule does not exist") {
            Formatter(programNode, json).format()
        }
    }

    @Test
    fun testGetRuleNames() {
        val factory = RulesFactory()
        val json = getJsonFromFile()
        val rules = factory.createRules(json)
        rules.forEach { rule -> println(rule.name) }
    }
}
