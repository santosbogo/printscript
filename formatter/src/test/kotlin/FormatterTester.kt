package test.kotlin

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.RulesFactory
import org.common.Location
import org.common.astnode.ProgramNode
import org.common.astnode.expressionnode.IdentifierNode
import org.common.astnode.expressionnode.LiteralNode
import org.common.astnode.expressionnode.LiteralValue
import org.common.astnode.statementnode.PrintStatementNode
import org.common.astnode.statementnode.VariableDeclarationNode
import org.junit.jupiter.api.Test
import java.io.File

class FormatterTester {

    @Test
    fun testFormat() {
        val variableDeclarationNode = VariableDeclarationNode(
            "VariableDeclarationNode",
            Location(1, 1),
            IdentifierNode("IdentifierNode", Location(1, 1), "x", "Number"),
            LiteralNode("LiteralNode", Location(1, 1), LiteralValue.NumberValue(42)),
            "let"
        )

        val printStatementNode = PrintStatementNode(
            "PrintStatementNode",
            Location(1, 1),
            LiteralNode("LiteralNode", Location(1, 1), LiteralValue.NumberValue(42))
        )

        val programNode = ProgramNode("ProgramNode", Location(1, 1), listOf(variableDeclarationNode, printStatementNode))

    }

    @Test
    fun testFactory() {
        val rulesFactory = RulesFactory()
        val filePath = "src/main/kotlin/rules/rulesExample.json"
        val jsonContent = File(filePath).readText()
        println(jsonContent)
        rulesFactory.createRules(Json.parseToJsonElement(jsonContent).jsonObject)
    }
}