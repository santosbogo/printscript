package org.astnode.expressionnode

import org.Location
import org.astnode.astnodevisitor.ASTNodeVisitor

class IdentifierNode(
    override val type: String,
    override val location: Location,
    val name: String,
    val dataType: String
) : ExpressionNode {
    override fun accept(visitor: ASTNodeVisitor) {
        visitor.visitIdentifierNode(this)
    }
}
