package org.shared.astnode.expressionnode

import org.common.Location
import org.common.astnode.astnodevisitor.ASTNodeVisitor
import org.common.astnode.astnodevisitor.types.VisitorResult

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
