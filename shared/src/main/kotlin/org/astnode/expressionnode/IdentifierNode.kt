package org.astnode.expressionnode

import org.Location
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.types.VisitorResult

class IdentifierNode(
    override val type: String,
    override val location: Location,
    val name: String,
    var dataType: String
) : ExpressionNode {
    override fun accept(visitor: ASTNodeVisitor): VisitorResult {
        return visitor.visitIdentifierNode(this)
    }
}
