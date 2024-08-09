package org.parser.semanticanalysis.semanticchecks

import org.astnode.ASTNode
import org.astnode.statementnode.AssignmentNode

class AssignmentTypeCheck : SemanticCheck {
    override fun check(node: ASTNode, symbolTable: MutableMap<String, Any>) {
        if (node.type == "AssignmentNode") {
            val assignmentNode = node as AssignmentNode
            val variableIdentifier = assignmentNode.identifierNode
            val variableType = assignmentNode.type

            // si la variable que quiero asignar no es del mismo tipo que la variable declarada, tiro error.
            if (symbolTable[variableIdentifier.name] != variableType) {
                throw Exception("Variable $variableIdentifier no es del tipo $variableType")
            }
        }
    }
}
