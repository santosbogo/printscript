package test.kotlin.org.v10

import java.io.File
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.Formatter
import org.FormatterVisitor
import org.Lexer
import org.Location
import org.Parser
import org.RulesFactory
import org.astnode.ProgramNode
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.statementnode.VariableDeclarationNode
import org.junit.jupiter.api.Test
import test.kotlin.TestReader

class FormatterTester {

    private fun getJsonFromFile(): JsonObject {
        val jsonContent = File("src/test/resources/rulesExample.json").readText()
        return Json.parseToJsonElement(jsonContent).jsonObject
    }

    private fun compareResults(
        node: ProgramNode,
        formater: Formatter,
        shouldSucceed: Boolean,
        file: File,
        solution: List<String>
    ) {
        try {
            val result = formater.format(node).toString().split("\n")
            if (!shouldSucceed) {
                assert(false) { "Expected an error but test passed for file ${file.name}" }
            }

            for (i in solution.indices) {
                assert(result[i] == solution[i]) {
                    "Mismatch in file \"${file.name}\" at " +
                        "line ${i + 1}: expected \"${solution[i]}\", found \"${result[i]}\""
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
        val examplesDir = File("src/test/resources/rulesExample.json")
        val reader = TestReader()
        val lexer = Lexer()
        val parser = Parser()

        val json = getJsonFromFile()

        examplesDir.listFiles { file -> file.isFile && file.extension == "txt" }?.forEach { file ->
            val (code, solution, shouldSucceed) = reader.readTokens(file.path)
            val lexerResult = lexer.tokenize(code)
            val parserResult = parser.parse(lexerResult.tokens)
            val programNode = parserResult.programNode!!
            val formater = Formatter(json, RulesFactory().createRulesForV11(json))
            compareResults(programNode, formater, shouldSucceed, file, solution)
        }
    }

    @Test
    fun testSingleFile() {
        val file = File("src/test/resources/examples-v10/manylinebreaks.txt")

        val reader = TestReader()
        val (code, solution, shouldSucceed) = reader.readTokens(file.path)

        val lexer = Lexer()
        val lexerResult = lexer.tokenize(code)

        val parser = Parser()
        val parserResult = parser.parse(lexerResult.tokens)
        val programNode = parserResult.programNode!!

        val formater = Formatter(getJsonFromFile())
        compareResults(programNode, formater, shouldSucceed, file, solution)
    }
    @Test
    fun testFormat() {
        val variableDeclarationNode = VariableDeclarationNode(
            "VariableDeclarationNode",
            Location(1, 1),
            IdentifierNode("IdentifierNode", Location(1, 1), "a", "number", "let"),
            LiteralNode("Literal", Location(1, 17), LiteralValue.NumberValue(10)),
            "let"
        )

        val programNode = ProgramNode("ProgramNode", Location(1, 1), listOf(variableDeclarationNode))

        // Get JSON from file
        val filePath = "src/test/resources/rulesExample.json"
        val jsonContent = File(filePath).readText()
        val json = Json.parseToJsonElement(jsonContent).jsonObject

        val formatter = Formatter(json)
        println(formatter.format(programNode))
    }

    @Test
    fun testWholeProgram() {
        val lexer = Lexer()
        val parser = Parser()
        val input = "let b: number = 10;b = 5;println(4);" +
            "let a: string = \"hola\";println(a);println(1 + 4);println(a + b);"

        val lexerResult = lexer.tokenize(input)
        val parserResult = parser.parse(lexerResult.tokens)
        val programNode = parserResult.programNode!!
        // Get JSON from file
        val filePath = "src/test/resources/rulesExample.json"
        val jsonContent = File(filePath).readText()
        val json = Json.parseToJsonElement(jsonContent).jsonObject

        val formatter = Formatter(json)
        println(formatter.format(programNode))
    }

    @Test
    fun testDoubleQuotes() {
        val lexer = Lexer()
        val parser = Parser()
        val input = "let a: string = \"hola\";"

        val lexerResult = lexer.tokenize(input)
        val parserResult = parser.parse(lexerResult.tokens)
        val programNode = parserResult.programNode!!

        val json = getJsonFromFile()

        val formatter = Formatter(json)
        println(formatter.format(programNode))
    }

    @Test
    fun testGetExpression() {
        val visitor = FormatterVisitor()
        val literalNode = LiteralNode("Literal", Location(1, 1), LiteralValue.NumberValue(10))
        val binaryExpressionNode = BinaryExpressionNode(
            "BinaryExpressionNode",
            Location(1, 1),
            LiteralNode("Literal", Location(1, 1), LiteralValue.NumberValue(10)),
            LiteralNode("Literal", Location(1, 1), LiteralValue.NumberValue(10)), "+"
        )
        val identifierNode = IdentifierNode("IdentifierNode", Location(1, 1), "a", "number", "let")
        val programNode = ProgramNode("ProgramNode", Location(1, 1), emptyList())
        val nodes = listOf(
            literalNode,
            binaryExpressionNode,
            identifierNode,
            programNode
        )
        nodes.forEach { node -> visitor.visit(node) }
    }
}
