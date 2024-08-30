package org.semanticanalysis.semanticchecks

import org.astnode.ASTNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.statementnode.AssignmentNode

class AssignmentKindCheck : SemanticCheck {
    override fun check(node: ASTNode, symbolTable: MutableMap<String, Pair<String, LiteralValue>>) {
        if (node.type == "AssignmentNode") {
            val assignmentNode = node as AssignmentNode
            val variableIdentifier = assignmentNode.identifier

            // chequea que no sea const. Si es const, no se puede reasignar
            if (variableIdentifier.kind == "const") {
                throw Exception("Cannot reassign a variable of type 'const'")
            }
        }
    }
}
