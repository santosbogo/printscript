package org.parser.astnode.expressionnode

import org.Location
import org.parser.astnode.expressionnode.expressionnodevisitor.ExpressionNodeVisitor

class BinaryExpression(
    override val type: String,
    override val location: Location,
    val left: ExpressionNode,
    val right: ExpressionNode,
    val operator: String
) : ExpressionNode {
    override fun accept(visitor: ExpressionNodeVisitor): Any {
        return visitor.visitBinaryExpression(this)
    }
}
