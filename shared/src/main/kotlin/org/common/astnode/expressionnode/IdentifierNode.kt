package org.shared.astnode.expressionnode

import org.shared.Location
import org.shared.astnode.astnodevisitor.ASTNodeVisitor

class IdentifierNode(
    override val type: String,
    override val location: Location,
    val name: String,
    var dataType: String
) : ExpressionNode {
    override fun accept(visitor: ASTNodeVisitor): LiteralValue {
        return visitor.visitIdentifierNode(this)
    }
}
