package org

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import org.astnode.ProgramNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import ruleBuilder.NewLineForIfAndBraceBuilder
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

class Formatter() {
    private val visitor: ASTNodeVisitor = FormatterVisitor()

    fun format(node: ProgramNode, rules: List<Rule>): FormatResult {
        val code: MutableList<String> = mutableListOf()
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

// This class creates from the JSON the rules that will be applied to the code
class RulesFactory() {

    fun getRules(content: String, version: String): List<Rule> {
        val json = RulesParser().parse(content)
        when (version) {
            "1.0" -> {
                return createRulesForV10(json)
            }

            "1.1" -> {
                return createRulesForV11(json)
            }
        }
        return error("Unsupported version")
    }

    private fun createRulesForV10(json: JsonObject): List<Rule> {
        val rulesMap = listOf(
            "space_before_colon" to SpaceBeforeColonBuilder(),
            "space_after_colon" to SpaceAfterColonBuilder(),
            "newline_after_println" to NewlineAfterPrintlnBuilder(),
            "newline_before_println" to NewlineBeforePrintlnBuilder(),
            "space_around_equals" to SpaceAroundEqualsBuilder(),
            "no_space_around_equals" to NoSpaceAroundEqualsBuilder(),
        )
        return createRules(json, rulesMap)
    }

    private fun createRulesForV11(json: JsonObject): List<Rule> {
        val rulesMap = listOf(
            "space_before_colon" to SpaceBeforeColonBuilder(),
            "space_after_colon" to SpaceAfterColonBuilder(),
            "newline_after_println" to NewlineAfterPrintlnBuilder(),
            "newline_before_println" to NewlineBeforePrintlnBuilder(),
            "space_around_equals" to SpaceAroundEqualsBuilder(),
            "no_space_around_equals" to NoSpaceAroundEqualsBuilder(),
            "number_of_spaces_indentation" to NumberOfSpacesIndentationBuilder(),
            "same_line_for_if_brace" to SameLineForIfAndBraceBuilder(),
            "same_line_for_else_brace" to SameLineForElseAndBraceBuilder(),
            "new_line_for_if_brace" to NewLineForIfAndBraceBuilder()
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
}

// This class is intended to parse their name for the variables to our names, so that any TCK may be implemented
class RulesParser() {
    fun parse(content: String): JsonObject {
        val theirJson: JsonObject = Json.parseToJsonElement(content).jsonObject
        val map = getMapOfTCK()

        val ourJson = buildJsonObject {
            for ((key, value) in theirJson) {
                if (key in ourFormat) { // If the key is already in our format
                    put(key, value)
                } else if (key in map) { // If the key isn't in our format, but expected
                    put(map[key]!!, value)
                }
            }
        }

        return ourJson
    }

    private fun getMapOfTCK(): Map<String, String> {
        // These are ignored because they are non-configurable
        // "mandatory-single-space-separation" , "mandatory-space-surrounding-operations" to "" , "mandatory-line-break-after-statement" to "",
        return mapOf(
            "enforce-spacing-around-equals" to "space_around_equals",
            "enforce-no-spacing-around-equals" to "no_space_around_equals",
            "enforce-spacing-after-colon-in-declaration" to "space_after_colon",
            "enforce-spacing-before-colon-in-declaration" to "space_before_colon",
            "line-breaks-after-println" to "newline_after_println",
            "if-brace-below-line" to "new_line_for_if_brace",
            "if-brace-same-line" to "same_line_for_if_brace",
            "indent-inside-if" to "number_of_spaces_indentation",
        )
    }

    private val ourFormat = listOf(
        "space_before_colon",
        "space_after_colon",
        "newline_after_println",
        "newline_before_println",
        "space_around_equals",
        "no_space_around_equals",
        "number_of_spaces_indentation",
        "same_line_for_if_brace",
        "same_line_for_else_brace"
    )
}
