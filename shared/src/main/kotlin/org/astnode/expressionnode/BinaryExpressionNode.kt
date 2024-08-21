package org.astnode.expressionnode

import org.Location
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.types.VisitorResult

class BinaryExpressionNode(
    override val type: String,
    override val location: Location,
    val left: ExpressionNode,
    val right: ExpressionNode,
    val operator: String
) : ExpressionNode {
    override fun accept(visitor: ASTNodeVisitor): VisitorResult {
        return visitor.visitBinaryExpressionNode(this)
    }

    override fun getType(symbolTable: MutableMap<String, LiteralValue>): String {
        val leftType = left.getType(symbolTable)
        val rightType = right.getType(symbolTable)

        return if (leftType == "string" || rightType == "string") {
            "string"
        } else {
            "number"
        }
    }
}
