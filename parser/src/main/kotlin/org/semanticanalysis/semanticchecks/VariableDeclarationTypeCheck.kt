package org.semanticanalysis.semanticchecks

import org.astnode.ASTNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.statementnode.VariableDeclarationNode

class VariableDeclarationTypeCheck : SemanticCheck {
    override fun check(node: ASTNode, symbolTable: MutableMap<String, LiteralValue>) {
        // chequeo que el tipo de la variable declarada sea el mismo que el tipo de la variable asignada
        if (node.type == "VariableDeclarationNode") {
            val variableDeclarationNode = node as VariableDeclarationNode
            val expressionNode = variableDeclarationNode.init
            val variableType = variableDeclarationNode.identifier.dataType

            // TODO (Definir el resto de casos ó hacer que el visitor actue)
            if (expressionNode.type == "Literal") {
                expressionNode as LiteralNode
                if (expressionNode.value.getType() != variableType) {
                    throw Exception("Variable $expressionNode no es del tipo $variableType")
                }
            }
        }
    }
}
