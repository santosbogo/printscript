package test.kotlin

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.Formatter
import org.RulesFactory
import org.common.Location
import org.common.astnode.ProgramNode
import org.common.astnode.expressionnode.IdentifierNode
import org.common.astnode.expressionnode.LiteralNode
import org.common.astnode.expressionnode.LiteralValue
import org.common.astnode.statementnode.VariableDeclarationNode
import org.junit.jupiter.api.Test
import java.io.File

class FormatterTester {

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

    @Test
    fun getRulesFromJson() {
        val rulesFactory = RulesFactory()
        val filePath = "src/main/kotlin/rulesExample.json"
        val jsonContent = File(filePath).readText()
        println(jsonContent)
        rulesFactory.createRules(Json.parseToJsonElement(jsonContent).jsonObject)
    }
}