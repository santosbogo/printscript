package org.common.astnode.statementnode

import org.common.Location
import org.common.astnode.astnodevisitor.ASTNodeVisitor
import org.common.astnode.astnodevisitor.types.VisitorResult
import org.shared.astnode.expressionnode.ExpressionNode
import org.shared.astnode.expressionnode.IdentifierNode
import org.shared.astnode.expressionnode.LiteralValue

class AssignmentNode(
    override val type: String,
    override val location: Location,
    val value: ExpressionNode,
    val identifierNode: IdentifierNode
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor): VisitorResult {
        return visitor.visitAssignmentNode(this)
    }
}
