package org

import org.astnode.ProgramNode
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.statementnode.AssignmentNode
import org.astnode.statementnode.VariableDeclarationNode
import org.junit.jupiter.api.Test
import org.semanticanalysis.SemanticVisitor
import org.semanticanalysis.semanticchecks.AssignmentTypeCheck
import org.semanticanalysis.semanticchecks.VariableDeclarationCheck
import org.semanticanalysis.semanticchecks.VariableDeclarationTypeCheck
import java.io.File
import java.io.StringReader
import kotlin.test.assertFailsWith

class ParserTesterV10 {

    @Test
    fun testFiles() {
        val reader = TestReader()
        val examplesDir = File("src/test/resources/examples-v10")

        examplesDir.listFiles { file -> file.isFile && file.extension == "txt" }?.forEach { file ->
            val (code, solution, shouldSucceed) = reader.readTokens(file.path)
            val lexer = Lexer(LexiconFactory().createLexiconV10(), StringReader(code))
            val parser = ParserFactory.createParserV10(lexer)
            val nodes = parser.collectAllASTNodes()
            try {
                if (!shouldSucceed) {
                    assert(false) {
                        "Expected an error but test passed for file ${file.name}"
                    }
                }
                for (i in nodes.indices) {
                    assert(nodes[i].type == solution[i]) {
                        "Mismatch in file ${file.name} at ${nodes[i].location}: " +
                            "expected ${solution[i]}, found ${nodes[i].type}"
                    }
                }
            } catch (e: Exception) {
                if (shouldSucceed) {
                    assert(false) { "Unexpected error in file ${file.name}: ${e.message}" }
                }
            }
        }
    }

    @Test
    fun testSingleFile() {
        val file = File("src/test/resources/examples-v10/variabledeclaration.txt")
        val reader = TestReader()
        val (code, solution, shouldSucceed) = reader.readTokens(file.path)
        val lexer = Lexer(LexiconFactory().createLexiconV10(), StringReader(code))
        val parser = ParserFactory.createParserV10(lexer)
        val nodes = parser.collectAllASTNodes()

        try {
            if (!shouldSucceed) {
                assert(false) { "Expected an error but test passed for file ${file.name}" }
            }
            for (i in nodes.indices) {
                assert(nodes[i].type == solution[i]) {
                    "Mismatch in file ${file.name} at ${nodes[i].location}: " +
                        "expected ${solution[i]}, found ${nodes[i].type}"
                }
            }
        } catch (e: Exception) {
            if (shouldSucceed) {
                assert(false) { "Unexpected error in file ${file.name}: ${e.message}" }
            }
        }
    }

    @Test
    fun testNoMatchingFormula() {
        val lexer = LexerFactory.createLexerV10(StringReader("let let a: number = 10;"))
        val parser = ParserFactory.createParserV10(lexer)
        val exception = assertFailsWith<Exception> {
            parser.collectAllASTNodes()
        }
        assert(exception.message?.contains("No formula matches the tokens") == true)
    }

    @Test
    fun testMissingSemicolon() {
        val lexer = LexerFactory.createLexerV10(StringReader("let a: number = 10; a = 5"))
        val parser = ParserFactory.createParserV10(lexer)

        val exception = assertFailsWith<Exception> {
            parser.collectAllASTNodes()
        }
        assert(exception.message?.contains("Unexpected end of input") == true)
    }

    @Test
    fun testSemanticVisitor() {
        val semanticVisitor = SemanticVisitor()
        val left = LiteralNode(
            "LiteralNode", Location(1, 1), LiteralValue.NumberValue(10)
        )
        val right = LiteralNode(
            "LiteralNode", Location(1, 1), LiteralValue.NumberValue(5)
        )
        val minusExpression = BinaryExpressionNode(
            "BinaryExpressionNode", Location(1, 1), left, right, "-"
        )
        val divisionExpression = BinaryExpressionNode(
            "BinaryExpressionNode", Location(1, 1), left, right, "/"
        )
        val multiplyExpression = BinaryExpressionNode(
            "BinaryExpressionNode", Location(1, 1), left, right, "*"
        )
        val nodes = listOf(minusExpression, divisionExpression, multiplyExpression)
        val programNode = ProgramNode("ProgramNode", Location(1, 1), nodes)
        programNode.accept(semanticVisitor)
        for (node in nodes) {
            node.accept(semanticVisitor)
        }
    }

    @Test
    fun testDivisionByZero() {
        val left = LiteralNode(
            "LiteralNode", Location(1, 1), LiteralValue.NumberValue(10)
        )
        val right = LiteralNode(
            "LiteralNode", Location(1, 1), LiteralValue.NumberValue(0)
        )
        val divisionByZero = BinaryExpressionNode(
            "BinaryExpressionNode", Location(1, 1), left, right, "/"
        )
        val semanticVisitor = SemanticVisitor()
        val exception = assertFailsWith<Exception> {
            divisionByZero.accept(semanticVisitor)
        }
        assert(exception.message?.contains("Division by zero") == true)
    }

    @Test
    fun testBreakAssignmentTypeCheck() {
        val symbolTable: MutableMap<String, Pair<String, LiteralValue>> = mutableMapOf(
            "x" to Pair("let", LiteralValue.NumberValue(10))
        )
        val assignmentNode = AssignmentNode(
            "AssignmentNode",
            Location(0, 0),
            LiteralNode(
                "Literal", Location(0, 0), LiteralValue.StringValue("Hi")
            ),
            IdentifierNode(
                "IdentifierNode",
                Location(0, 0),
                "x",
                "number",
                "let"
            )
        )
        val exception = assertFailsWith<Exception> {
            AssignmentTypeCheck().check(assignmentNode, symbolTable)
        }
        assert(exception.message?.contains("Variable x no es del tipo number") == true)
    }

    @Test
    fun testBreakVariableDeclarationTypeCheck() {
        val symbolTable: MutableMap<String, Pair<String, LiteralValue>> = mutableMapOf(
            "x" to Pair("let", LiteralValue.NumberValue(10))
        )
        val variableDeclarationNode = VariableDeclarationNode(
            "VariableDeclarationNode",
            Location(0, 0),
            IdentifierNode(
                "IdentifierNode",
                Location(0, 0),
                "x",
                "number",
                "let"
            ),
            LiteralNode(
                "Literal", Location(0, 0), LiteralValue.StringValue("Hi")
            ),
            "let"
        )
        val exception = assertFailsWith<Exception> {
            VariableDeclarationTypeCheck().check(variableDeclarationNode, symbolTable)
        }
        assert(exception.message?.contains("Variable x no es del tipo number") == true)
    }

    @Test
    fun testBreakVariableDeclarationCheck() {
        val symbolTable: MutableMap<String, Pair<String, LiteralValue>> = mutableMapOf(
            "x" to Pair("let", LiteralValue.NumberValue(10))
        )
        val variableDeclarationNode = VariableDeclarationNode(
            "VariableDeclarationNode",
            Location(0, 0),
            IdentifierNode(
                "IdentifierNode",
                Location(0, 0),
                "x",
                "number",
                "let"
            ),
            LiteralNode(
                "Literal", Location(0, 0), LiteralValue.StringValue("Hi")
            ),
            "let"
        )
        val exception = assertFailsWith<Exception> {
            VariableDeclarationCheck().check(variableDeclarationNode, symbolTable)
        }
        assert(exception.message?.contains("Variable x ya fue declarada") == true)
    }
}
