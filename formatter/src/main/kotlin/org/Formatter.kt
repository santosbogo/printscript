package org

import kotlinx.serialization.json.JsonObject
import org.common.astnode.ProgramNode
import org.common.astnode.astnodevisitor.ASTNodeVisitor
import rules.Rule
import ruleBuilder.*

class Formatter(private val node: ProgramNode, json : JsonObject, private val visitor: ASTNodeVisitor = FormatterVisitor()) {
    private val rules = RulesFactory().createRules(json)

    fun format(): String {
        val code: MutableList<String> = mutableListOf()
        var result : String = ""

        // Takes each AST and gets its string representation
        node.statements.forEach {
            code += visitor.visit(it).toString()
        }
        // Applies rules to each statement of code
        code.forEach { line ->
            result += applyRules(line) + "\n"
        }
        return result
    }

    private fun applyRules(line: String): String {
        rules.forEach { rule ->
            return rule.applyRule(line)
        }
        return line
    }
}

class RulesFactory(private val rulesMap: List<Pair<String, RuleBuilder>> = defaultRules()) {

    // Get Rules from JSON
    fun createRules(json: JsonObject): List<Rule> {
        val rules = mutableListOf<Rule>()
        for ((key, value) in json) {
            if (value.toString() == "false") { continue }
            val ruleBuilder = rulesMap.find { it.first == key }?.second
            val rule = ruleBuilder?.buildRule(key, value.toString())

            if (rule != null) {
                rules.add(rule)
            } else {
                error("Rule does not exist")
            }
        }
        return rules
    }
}

fun defaultRules() : List<Pair<String, RuleBuilder>> {
    return listOf(
        "space_before_colon" to SpaceBeforeColonBuilder(),
        "newline_before_println" to NewlineBeforePrintlnBuilder(),
        "space_after_colon" to SpaceAfterColonBuilder(),
        "space_around_equals" to SpaceAroundEqualsBuilder(),
    )
}

