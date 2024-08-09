package org.semanticanalysis.semanticchecks

import org.shared.astnode.ASTNode
import org.shared.astnode.statementnode.AssignmentNode
import org.parser.semanticanalysis.semanticchecks.SemanticCheck

class UndeclaredVariableCheck : SemanticCheck {
    override fun check(node: ASTNode, symbolTable: MutableMap<String, Any>) {
        // si quiero asignar una variable que no existe todavia, tiro error.
        if (node.type == "AssignmentNode") {
            val assignmentNode = node as AssignmentNode
            val variableIdentifier = assignmentNode.identifierNode
            if (!symbolTable.containsKey(variableIdentifier.name)) {
                throw Exception("Variable $variableIdentifier no fue declarada")
            }
        }
    }
}
