package org.shared.astnode.statementnode

import org.shared.Location
import org.shared.astnode.astnodevisitor.ASTNodeVisitor
import org.shared.astnode.expressionnode.ExpressionNode
import org.shared.astnode.expressionnode.IdentifierNode

class AssignmentNode(
    override val type: String,
    override val location: Location,
    val value: ExpressionNode,
    val identifierNode: IdentifierNode
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor) {
        visitor.visitAssignmentNode(this)
    }
}
