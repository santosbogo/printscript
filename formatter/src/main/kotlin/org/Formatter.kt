package org

import kotlinx.serialization.json.JsonObject
import org.astnode.ProgramNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import ruleBuilder.NewlineAfterPrintlnBuilder
import ruleBuilder.NewlineBeforePrintlnBuilder
import ruleBuilder.NoSpaceAroundEqualsBuilder
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
import kotlin.collections.forEach

class Formatter(private val version: String) {
    private val visitor: ASTNodeVisitor = FormatterVisitor()

    fun format(node: ProgramNode, json: JsonObject): FormatResult {
        val code: MutableList<String> = mutableListOf()
        val rules = RulesFactory().getRules(json, version)
        var result = ""

        // Takes each AST and gets its string representation
        node.statements.forEach {
            code += visitor.visit(it).toString()
        }

        // Applies rules to each statement of code
        code.forEach { line ->
            result += applyRules(line, rules)
        }

        return FormatResult(result)
    }

    private fun applyRules(line: String, rules: List<Rule>): String {
        var modifiedLine = line
        rules.forEach { rule ->
            modifiedLine = rule.applyRule(modifiedLine)
        }
        return modifiedLine
    }
}

class FormatterFactory() {

    fun createFormatterV10(): Formatter {
        return Formatter("1.0")
    }

    fun createFormatterV11(): Formatter {
        return Formatter("1.1")
    }
}

class RulesFactory() {

    fun getRules(json: JsonObject, string: String): List<Rule> {
        when (string) {
            "1.0" -> {
                return createRulesForV10(json)
            }

            "1.1" -> {
                return createRulesForV11(json)
            }
        }
        return error("Unsupported version")
    }

    fun createRulesForV10(json: JsonObject): List<Rule> {
        val rulesMap = listOf(
            "space_before_colon" to SpaceBeforeColonBuilder(),
            "line-breaks-after-println" to NewlineAfterPrintlnBuilder(),
            "newline_before_println" to NewlineBeforePrintlnBuilder(),
            "space_after_colon" to SpaceAfterColonBuilder(),
            "enforce-spacing-around-equals" to SpaceAroundEqualsBuilder(),
            "enforce-no-spacing-around-equals" to NoSpaceAroundEqualsBuilder(),
        )
        return createRules(json, rulesMap)
    }

    fun createRulesForV11(json: JsonObject): List<Rule> {
        val rulesMap = listOf(
            "space_before_colon" to SpaceBeforeColonBuilder(),
            "line-breaks-after-println" to NewlineAfterPrintlnBuilder(),
            "newline_before_println" to NewlineBeforePrintlnBuilder(),
            "space_after_colon" to SpaceAfterColonBuilder(),
            "enforce-spacing-around-equals" to SpaceAroundEqualsBuilder(),
            "enforce-no-spacing-around-equals" to NoSpaceAroundEqualsBuilder(),
            "number_of_spaces_indentation" to NumberOfSpacesIndentationBuilder(),
            "same_line_for_if_brace" to SameLineForIfAndBraceBuilder(),
            "same_line_for_else_brace" to SameLineForElseAndBraceBuilder(),
        )
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
        rules.add(OnlyOneSpacePermited())

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

    private fun getMapOfTCK(): Map<String, String> {
        return mapOf(
            "enforce-spacing-around-equals" to "space_around_equals",
            "enforce-no-spacing-around-equals" to "no_space_around_equals",
            "enforce-spacing-after-colon-in-declaration" to "space_after_colon",
            "enforce-spacing-before-colon-in-declaration" to "space_before_colon",
            "mandatory-single-space-separation" to "",
        )
    }
}
