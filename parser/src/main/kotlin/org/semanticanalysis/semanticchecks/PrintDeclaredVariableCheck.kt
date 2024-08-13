package org.semanticanalysis.semanticchecks

import org.parser.semanticanalysis.semanticchecks.SemanticCheck
import org.shared.astnode.ASTNode
import org.shared.astnode.expressionnode.BinaryExpressionNode
import org.shared.astnode.expressionnode.IdentifierNode
import org.common.astnode.statementnode.PrintStatementNode

class PrintDeclaredVariableCheck : SemanticCheck {
    override fun check(node: ASTNode, symbolTable: MutableMap<String, Any>) {
        if (node.type == "PrintStatementNode") {
            val printNode = node as PrintStatementNode
            val expression = printNode.value

            if (expression is BinaryExpressionNode) {
                val left = expression.left
                val right = expression.right
                check(left, symbolTable)
                check(right, symbolTable)
            } else if (expression is IdentifierNode) {
                if (!symbolTable.containsKey(expression.name)) {
                    throw Exception("Variable ${expression.name} no declarada")
                }
            }
        }
    }
}