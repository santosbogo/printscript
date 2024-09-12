package org

import kotlinx.serialization.json.JsonObject
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
import rules.RulesParser
import rules.SpaceAfterAndBeforeOperators
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.iterator

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
