package org.semanticanalysis.semanticchecks

import org.astnode.ASTNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.expressionnode.ReadInputNode
import org.astnode.statementnode.VariableDeclarationNode

class VariableDeclarationTypeCheck : SemanticCheck {
    override fun check(node: ASTNode, symbolTable: MutableMap<String, Pair<String, LiteralValue>>) {
        // chequeo que el tipo de la variable declarada sea el mismo que el tipo de la variable asignada
        if (node.type == "VariableDeclarationNode") {
            val variableDeclarationNode = node as VariableDeclarationNode
            val expressionNode = variableDeclarationNode.init
            val variableType = variableDeclarationNode.identifier.dataType
            val expressionType = expressionNode.getType(symbolTable)

            if (expressionType == "null") {
                return
            }

            if (expressionNode is ReadInputNode && expressionType != "string") {
                throw Exception("ReadInputNode message must be of type string")
            }

            if (expressionType != variableType && expressionType != "Undefined") {
                throw Exception(
                    "Variable ${variableDeclarationNode.identifier.name}" +
                        " no es del tipo $variableType " +
                        "y no puede ser asignada con un valor de tipo $expressionType"
                )
            }
        }
    }
}
