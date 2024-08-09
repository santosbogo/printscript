package org.shared.astnode.statementnode

import org.shared.Location
import org.shared.astnode.astnodevisitor.ASTNodeVisitor
import org.shared.astnode.expressionnode.ExpressionNode

class PrintStatementNode(
    override val type: String,
    override val location: Location,
    val value: ExpressionNode
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor) {
        visitor.visitPrintStatementNode(this)
    }
}
