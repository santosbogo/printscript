package org.common.astnode.statementnode

import org.common.Location
import org.common.astnode.astnodevisitor.types.VisitorResult
import org.common.astnode.astnodevisitor.ASTNodeVisitor
import org.shared.astnode.expressionnode.ExpressionNode

class PrintStatementNode(
    override val type: String,
    override val location: Location,
    val value: ExpressionNode
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor): VisitorResult {
        return visitor.visitPrintStatementNode(this)
    }
}
