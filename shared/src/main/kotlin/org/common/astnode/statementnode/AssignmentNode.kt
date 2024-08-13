package org.shared.astnode.statementnode

import org.common.Location
import org.shared.astnode.astnodevisitor.ASTNodeVisitor
import org.shared.astnode.expressionnode.ExpressionNode
import org.shared.astnode.expressionnode.IdentifierNode
import org.shared.astnode.expressionnode.LiteralValue

class AssignmentNode(
    override val type: String,
    override val location: Location,
    val value: ExpressionNode,
    val identifierNode: IdentifierNode
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor): LiteralValue? {
        return visitor.visitAssignmentNode(this)
    }
}
