package org

import kotlinx.serialization.json.JsonObject
import ruleBuilder.*
import rules.*

class RulesFactory {

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

    fun createRulesForV10(json: JsonObject): List<Rule> {
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

    fun createRulesForV11(json: JsonObject): List<Rule> {
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
            "mandatory-space-surrounding-operations" to "",
            "mandatory-line-break-after-statement" to "",
            "line-breaks-after-println" to "line-breaks-after-println",
            "if-brace-below-line" to "same_line_for_if_brace",
            "if-brace-same-line" to "same_line_for_else_brace",
            "indent-inside-if" to "number_of_spaces_indentation"

        )
    }
}
