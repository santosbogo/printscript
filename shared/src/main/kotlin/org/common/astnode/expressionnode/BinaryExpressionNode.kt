package org.shared.astnode.expressionnode

import org.shared.Location
import org.shared.astnode.astnodevisitor.ASTNodeVisitor

class BinaryExpressionNode(
    override val type: String,
    override val location: Location,
    val left: ExpressionNode,
    val right: ExpressionNode,
    val operator: String
) : ExpressionNode {
    override fun accept(visitor: ASTNodeVisitor): LiteralValue {
        return visitor.visitBinaryExpressionNode(this)
    }
}
