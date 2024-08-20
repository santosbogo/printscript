package org.semanticanalysis.semanticchecks

import org.common.astnode.ASTNode
import org.common.astnode.statementnode.VariableDeclarationNode

class VariableDeclarationTypeCheck : SemanticCheck {
    override fun check(node: ASTNode, symbolTable: MutableMap<String, Any>) {
        // chequeo que el tipo de la variable declarada sea el mismo que el tipo de la variable asignada
        if (node.type == "VariableDeclaration") {
            val variableDeclarationNode = node as VariableDeclarationNode
            val variableExpression = variableDeclarationNode.init
            val variableType = variableDeclarationNode.type

            // si el tipo de la variable que quiero asignar no es el mismo que el tipo de la variable declarada, tiro error.
            if (variableExpression.type != variableType) {
                throw Exception("Variable $variableExpression no es del tipo $variableType")
            }
        }
    }
}
