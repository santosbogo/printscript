package org

import kotlinx.serialization.json.JsonObject
import org.astnode.ProgramNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import ruleBuilder.NewlineBeforePrintlnBuilder
import ruleBuilder.NumberOfSpacesIndentationBuilder
import ruleBuilder.RuleBuilder
import ruleBuilder.SameLineForElseAndBraceBuilder
import ruleBuilder.SameLineForIfAndBraceBuilder
import ruleBuilder.SpaceAfterColonBuilder
import ruleBuilder.SpaceAroundEqualsBuilder
import ruleBuilder.SpaceBeforeColonBuilder
import rules.NewLineAfterBrace
import rules.NewLineAfterSemiColon
import rules.OnlyOneSpacePermited
import rules.Rule
import rules.SpaceAfterAndBeforeOperators

class Formatter(
    private val node: ProgramNode,
    json: JsonObject,
    private val rules: List<Rule> = RulesFactory().createRulesForV10(json),
    private val visitor: ASTNodeVisitor = FormatterVisitor(),
) {

    fun format(): FormatResult {
        val code: MutableList<String> = mutableListOf()
        var result = ""

        // Takes each AST and gets its string representation
        node.statements.forEach {
            code += visitor.visit(it).toString()
        }

        // Applies rules to each statement of code
        code.forEach { line ->
            result += applyRules(line)
        }

        return FormatResult(result)
    }

    private fun applyRules(line: String): String {
        var modifiedLine = line
        rules.forEach { rule ->
            modifiedLine = rule.applyRule(modifiedLine)
        }
        return modifiedLine
    }
}

class RulesFactory() {

    fun createRulesForV10(json: JsonObject): List<Rule> {
        val rulesMap = rulesForV10()
        return createRules(json, rulesMap)
    }

    fun createRulesForV11(json: JsonObject): List<Rule> {
        val rulesMap = rulesForV11()
        return createRules(json, rulesMap)
    }

    // Get Rules from JSON
    private fun createRules(json: JsonObject, rulesMap: List<Pair<String, RuleBuilder>>): List<Rule> {
        val rules = mutableListOf<Rule>()

        // Add non configurable rules
        rules.add(OnlyOneSpacePermited())
        rules.add(NewLineAfterSemiColon())
        rules.add(SpaceAfterAndBeforeOperators())
        rules.add(NewLineAfterBrace())

        for ((key, value) in json) {
            if (value.toString() == "false") {
                continue
            }
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

    private fun rulesForV10(): List<Pair<String, RuleBuilder>> {
        return listOf(
            "space_before_colon" to SpaceBeforeColonBuilder(),
            "newline_before_println" to NewlineBeforePrintlnBuilder(),
            "space_after_colon" to SpaceAfterColonBuilder(),
            "space_around_equals" to SpaceAroundEqualsBuilder(),
        )
    }

    private fun rulesForV11(): List<Pair<String, RuleBuilder>> {
        return listOf(
            "space_before_colon" to SpaceBeforeColonBuilder(),
            "newline_before_println" to NewlineBeforePrintlnBuilder(),
            "space_after_colon" to SpaceAfterColonBuilder(),
            "space_around_equals" to SpaceAroundEqualsBuilder(),
            "number_of_spaces_indentation" to NumberOfSpacesIndentationBuilder(),
            "same_line_for_if_brace" to SameLineForIfAndBraceBuilder(),
            "same_line_for_else_brace" to SameLineForElseAndBraceBuilder(),
        )
    }
}
