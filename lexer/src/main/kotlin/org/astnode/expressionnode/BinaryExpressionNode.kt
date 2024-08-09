package org.astnode.expressionnode

import org.Location
import org.astnode.astnodevisitor.ASTNodeVisitor

class BinaryExpressionNode(
    override val type: String,
    override val location: Location,
    val left: ExpressionNode,
    val right: ExpressionNode,
    val operator: String
) : ExpressionNode {
    override fun accept(visitor: ASTNodeVisitor) {
        visitor.visitBinaryExpressionNode(this)
    }
}
