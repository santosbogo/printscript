package org.astnode.statementnode

import org.Location
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.types.VisitorResult
import org.astnode.expressionnode.ExpressionNode
import org.astnode.expressionnode.IdentifierNode

class AssignmentNode(
    override val type: String,
    override val location: Location,
    val value: ExpressionNode,
    val identifier: IdentifierNode
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor): VisitorResult {
        return visitor.visit(this)
    }
}
