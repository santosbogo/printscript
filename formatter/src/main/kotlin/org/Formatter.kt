package org

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import org.astnode.ASTNode
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

class Formatter(private val nodeIterator: Iterator<ASTNode>) {
    private val visitor: ASTNodeVisitor = FormatterVisitor()

    fun format(rules: List<Rule>): FormatResult {
        val code: MutableList<String> = mutableListOf()
        var result = ""

        // Takes each AST and gets its string representation
        while (nodeIterator.hasNext()) {
            val node = nodeIterator.next()
            code.add(visitor.visit(node).toString())
        }

        // Applies rules to each statement of code
        code.forEach {
                line -> result += applyRules(line, rules)
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
