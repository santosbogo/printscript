package org.semanticanalysis.semanticchecks

import org.common.astnode.ASTNode
import org.common.astnode.statementnode.AssignmentNode

class UndeclaredVariableCheck : SemanticCheck {
    override fun check(node: ASTNode, symbolTable: MutableMap<String, Any>) {
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
