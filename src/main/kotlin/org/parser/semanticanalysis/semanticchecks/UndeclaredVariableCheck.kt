package org.parser.semanticanalysis.semanticchecks

import org.parser.astnode.ASTNode
import org.parser.astnode.statementnode.AssignmentNode

class UndeclaredVariableCheck: SemanticCheck {
    override fun check(node: ASTNode, symbolTable: MutableMap<String, Any>) {
        //si quiero asignar una variable que no existe todavia, tiro error.
        if (node.type == "AssignmentNode") {
            val assignmentNode = node as AssignmentNode
            val variableIdentifier = assignmentNode.identifier
            if (!symbolTable.containsKey(variableIdentifier.name)) {
                throw Exception("Variable $variableIdentifier no fue declarada")
            }
        }
    }
}