package org.semanticanalysis.semanticchecks

import org.astnode.ASTNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.statementnode.AssignmentNode

class AssignmentTypeCheck : SemanticCheck {
    override fun check(node: ASTNode, symbolTable: MutableMap<String, LiteralValue>) {
        if (node.type == "AssignmentNode") {
            val assignmentNode = node as AssignmentNode
            val variableIdentifier = assignmentNode.identifierNode
            val expression = assignmentNode.value

            val variableType = symbolTable[variableIdentifier.name]?.getType()
            val expressionType = expression.getType(symbolTable)

            if (variableType != expressionType) {
                throw Exception("Variable $variableIdentifier no es del tipo $variableType")
            }
        }
    }
}
