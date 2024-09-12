package org

import org.astnode.ASTNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.iterator.PrintScriptIterator
import rules.Rule
import kotlin.collections.forEach

class Formatter(private val nodeIterator: PrintScriptIterator<ASTNode>) {
    private val visitor: ASTNodeVisitor = FormatterVisitor()

    fun format(rules: List<Rule>): FormatResult {
        val code: MutableList<String> = mutableListOf()
        var result = ""

        // Takes each AST and gets its string representation
        while (nodeIterator.hasNext()) {
            val node = nodeIterator.next()!!
            code.add(visitor.visit(node).toString())
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
