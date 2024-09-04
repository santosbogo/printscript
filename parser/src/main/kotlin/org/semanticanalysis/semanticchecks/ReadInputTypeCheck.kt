package org.semanticanalysis.semanticchecks

import org.astnode.ASTNode
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.expressionnode.ReadInputNode

class ReadInputTypeCheck : SemanticCheck {
    override fun check(node: ASTNode, symbolTable: MutableMap<String, Pair<String, LiteralValue>>) {
        // si el mensaje pasado no es de tipo 'string', rompe.
        if (node is ReadInputNode) {
            val messageType = node.message.getType(symbolTable)
            if (messageType != "string") {
                throw Exception("ReadInputNode message must be of type string")
            }
        }
    }

    private fun getExpressionType(expression: ASTNode, symbolTable: MutableMap<String, Pair<String, LiteralValue>>): String {
        // helper fun para obtener el tipo de una binary expression.
        return when (expression) {
            is LiteralValue -> expression.type
            is IdentifierNode -> symbolTable[expression.name]?.first ?: throw Exception("Variable ${expression.name} not declared")
            is BinaryExpressionNode -> {
                val leftType = getExpressionType(expression.left, symbolTable)
                val rightType = getExpressionType(expression.right, symbolTable)

                // Check if both types are strings, or a combination of string and number
                if ((leftType == "string" && rightType == "string") || (leftType == "string" && rightType == "number") || (leftType == "number" && rightType == "string")) {
                    "string"
                } else {
                    throw Exception("Invalid expression type")
                }
            }
            else -> throw Exception("Invalid expression type")
        }
    }
}
