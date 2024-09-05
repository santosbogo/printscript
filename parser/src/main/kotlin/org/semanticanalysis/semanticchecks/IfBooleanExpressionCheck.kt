package org.semanticanalysis.semanticchecks

import org.astnode.ASTNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.statementnode.IfNode

class IfBooleanExpressionCheck : SemanticCheck {
    override fun check(node: ASTNode, symbolTable: MutableMap<String, Pair<String, LiteralValue>>) {
        if (node.type == "IfNode") {
            val ifNode = node as IfNode
            val condition = ifNode.boolean

            val conditionType = condition.getType(symbolTable)

            if (conditionType != "boolean") {
                throw Exception(
                    "Illegal expression in if statement at ${ifNode.location}." +
                        " Condition is not of type boolean in ${condition.location}."
                )
            }
        }
    }
}
