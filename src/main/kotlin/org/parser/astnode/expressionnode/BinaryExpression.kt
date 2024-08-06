package org.parser.astnode.expressionnode

import org.utils.Location
import org.parser.astnode.astnodevisitor.ASTNodeVisitor

class BinaryExpression(
    override val type: String,
    override val location: Location,
    val left: ExpressionNode,
    val right: ExpressionNode,
    val operator: String
) : ExpressionNode {
    override fun accept(visitor: ASTNodeVisitor) {
        TODO("Not yet implemented")
    }
}