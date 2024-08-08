package org.astnode.statementnode

import org.Location
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.expressionnode.ExpressionNode
import org.astnode.expressionnode.IdentifierNode

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
