package org

import kotlinx.serialization.json.JsonObject
import org.astnode.ProgramNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import ruleBuilder.NewlineBeforePrintlnBuilder
import ruleBuilder.RuleBuilder
import ruleBuilder.SpaceAfterColonBuilder
import ruleBuilder.SpaceAroundEqualsBuilder
import ruleBuilder.SpaceBeforeColonBuilder
import rules.NewLineAfterSemiColon
import rules.OnlyOneSpacePermited
import rules.Rule
import rules.SpaceAfterAndBeforeOperators

class Formatter(
    private val node: ProgramNode,
    json: JsonObject,
    private val visitor: ASTNodeVisitor = FormatterVisitor()
) {
    private val rules = RulesFactory().createRules(json)

    fun format(): FormattedCode {
        val code: MutableList<String> = mutableListOf()
        var result = ""

        // Takes each AST and gets its string representation
        node.statements.forEach { code += visitor.visit(it).toString() }

        // Applies rules to each statement of code
        code.forEach { line -> result += applyRules(line) }

        return FormattedCode(result)
    }

    private fun applyRules(line: String): String {
        var modifiedLine = line
        rules.forEach { rule ->
            modifiedLine = rule.applyRule(modifiedLine)
        }
        return modifiedLine
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
        // Add non configurable rules
        rules.add(OnlyOneSpacePermited())
        rules.add(NewLineAfterSemiColon())
        rules.add(SpaceAfterAndBeforeOperators())
        return rules
    }
}

fun defaultRules(): List<Pair<String, RuleBuilder>> {
    return listOf(
        "space_before_colon" to SpaceBeforeColonBuilder(),
        "newline_before_println" to NewlineBeforePrintlnBuilder(),
        "space_after_colon" to SpaceAfterColonBuilder(),
        "space_around_equals" to SpaceAroundEqualsBuilder(),
    )
}
