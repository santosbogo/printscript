package org

import kotlinx.serialization.json.JsonObject
import rules.Rule
import org.common.astnode.ProgramNode
import org.common.astnode.astnodevisitor.ASTNodeVisitor
import rules.ruleBuilder.NewlineBeforePrintlnBuilder
import rules.ruleBuilder.RuleBuilder
import rules.ruleBuilder.SpaceAfterColonBuilder

class Formatter(private val node: ProgramNode, json : JsonObject, private val visitor: ASTNodeVisitor = FormatterVisitor()) {
    private val rules = RulesFactory().createRules(json)

    fun format(): String {
        var result: String = ""
        // Takes each AST
        node.statements.forEach { // Que el visitor guarde el resultado
            result += visitor.visit(it).toString()
            result += "\n"
        }
        return result
    }
}

class RulesFactory(private val rulesMap: List<Pair<String, RuleBuilder>> = defaultRules()) {

    // Get Rules from JSON
    fun createRules(json: JsonObject): List<Rule> {
        val rules = mutableListOf<Rule>()
        for ((key, value) in json) {
            val ruleBuilder = rulesMap.find { it.first == key }?.second
            val rule = ruleBuilder?.buildRule(key, value.toString())

            if (rule != null) {
                rules.add(rule)
                println(rule.name)
            } else {
                println("Rule does not exist")
            }
        }
        return rules
    }
}

fun defaultRules() : List<Pair<String, RuleBuilder>> {
    return listOf(
        "space_before_colon" to SpaceAfterColonBuilder(),
        "newline_before_println" to NewlineBeforePrintlnBuilder(),
    )
}

