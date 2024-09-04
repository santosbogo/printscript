package org.semanticanalysis.semanticchecks

import org.astnode.ASTNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.statementnode.AssignmentNode

class AssignmentTypeCheck : SemanticCheck {
    override fun check(node: ASTNode, symbolTable: MutableMap<String, Pair<String, LiteralValue>>) {
        if (node.type == "AssignmentNode") {
            val assignmentNode = node as AssignmentNode
            val variableIdentifier = assignmentNode.identifier
            val expression = assignmentNode.value

            val variableType = symbolTable[variableIdentifier.name]?.second?.getType()
            val expressionType = expression.getType(symbolTable)

            if (variableType != expressionType && variableType != "null") {
                throw Exception("Variable ${variableIdentifier.name} no es del tipo $variableType")
            }
        }
    }
}
