package org

import java.io.File
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.astnode.ProgramNode
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.statementnode.VariableDeclarationNode
import org.junit.jupiter.api.Test
import java.io.FileInputStream

class FormatterTesterV10 {

    private fun getJsonFromFile(): JsonObject {
        val jsonContent = File("src/test/resources/rulesExample.json").readText()
        return Json.parseToJsonElement(jsonContent).jsonObject
    }

    private fun compareResults(
        formater: Formatter,
        shouldSucceed: Boolean,
        file: File,
        solution: List<String>
    ) {
        try {
            val rules = RulesFactory().getRules(getJsonFromFile().toString(), "1.0")
            val result = formater.format(rules).toString().split("\n")
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

        examplesDir.listFiles { file -> file.isFile && file.extension == "txt" }?.forEach { file ->
            val (code, solution, shouldSucceed) = reader.readTokens(file.path)
            val lexer = LexerFactory.createLexerV10(FileInputStream(code))
            val parser = ParserFactory.createParserV10(lexer)

            val formatter = Formatter(parser)
            compareResults(formatter, shouldSucceed, file, solution)
        }
    }

    @Test
    fun testSingleFile() {
        val file = File("src/test/resources/examples-v10/manylinebreaks.txt")

        val reader = TestReader()
        val (code, solution, shouldSucceed) = reader.readTokens(file.path)

        val lexer = LexerFactory.createLexerV10(FileInputStream(code))
        val parser = ParserFactory.createParserV10(lexer)

        val formatter = Formatter(parser)
        val rules = RulesFactory().getRules(getJsonFromFile().toString(), "1.0")
        println(formatter.format(rules))
    }
    @Test
    fun testFormat() {
        val input = "let a: number = 10;"
        val lexer = LexerFactory.createLexerV10(FileInputStream(input))
        val parser = ParserFactory.createParserV10(lexer)

        val formatter = Formatter(parser)

        // Get JSON from file
        val filePath = "src/test/resources/rulesExample.json"
        val jsonContent = File(filePath).readText()
        val json = Json.parseToJsonElement(jsonContent).jsonObject

        val rules = RulesFactory().getRules(json.toString(), "1.0")
        println(formatter.format(rules))
    }

    @Test
    fun testWholeProgram() {
        val input = "let b: number = 10;b = 5;println(4);" +
            "let a: string = \"hola\";println(a);println(1 + 4);println(a + b);"

        val lexer = LexerFactory.createLexerV10(FileInputStream(input))
        val parser = ParserFactory.createParserV10(lexer)

        // Get JSON from file
        val filePath = "src/test/resources/rulesExample.json"
        val jsonContent = File(filePath).readText()
        val json = Json.parseToJsonElement(jsonContent).jsonObject

        val formatter = Formatter(parser)
        val rules = RulesFactory().getRules(json.toString(), "1.0")
        println(formatter.format(rules))
    }

    @Test
    fun testDoubleQuotes() {
        val input = "let a: string = \"hola\";"

        val lexer = LexerFactory.createLexerV10(FileInputStream(input))
        val parser = ParserFactory.createParserV10(lexer)
        val formatter = Formatter(parser)
        val json = getJsonFromFile()
        val rules = RulesFactory().getRules(json.toString(), "1.0")
        println(formatter.format(rules))
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
