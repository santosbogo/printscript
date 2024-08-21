package org.semanticanalysis.semanticchecks

import org.astnode.ASTNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.statementnode.AssignmentNode

class UndeclaredVariableCheck : SemanticCheck {
    override fun check(node: ASTNode, symbolTable: MutableMap<String, LiteralValue>) {
        // si quiero asignar una variable que no existe todavía, tiro error.
        if (node.type == "AssignmentNode") {
            val assignmentNode = node as AssignmentNode
            val variableIdentifier = assignmentNode.identifierNode
            if (!symbolTable.containsKey(variableIdentifier.name)) {
                throw Exception("Variable $variableIdentifier no fue declarada")
            }
        }
    }
}
