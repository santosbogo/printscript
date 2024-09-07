package org.astnode.expressionnode

import org.Location
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.VisitorResult

class BinaryExpressionNode(
    override val type: String,
    override val location: Location,
    val left: ExpressionNode,
    val right: ExpressionNode,
    val operator: String
) : ExpressionNode {
    override fun accept(visitor: ASTNodeVisitor): VisitorResult {
        return visitor.visit(this)
    }

    override fun getType(symbolTable: MutableMap<String, Pair<String, LiteralValue>>): String {
        val leftType = left.getType(symbolTable)
        val rightType = right.getType(symbolTable)
        val validTypes = listOf("string", "number")

        for (type in listOf(leftType, rightType)) {
            if (type !in validTypes) {
                throw Exception("Invalid type $type in binary expression")
            }
        }

        return if (leftType == "string" || rightType == "string") {
            "string"
        } else {
            "number"
        }
    }

    override fun toString(): String {
        return left.toString() + " " + operator + " " + right.toString()
    }
}
