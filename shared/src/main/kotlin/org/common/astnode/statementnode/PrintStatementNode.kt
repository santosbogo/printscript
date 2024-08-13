package org.shared.astnode.statementnode

import org.common.Location
import org.shared.astnode.astnodevisitor.ASTNodeVisitor
import org.shared.astnode.expressionnode.ExpressionNode
import org.shared.astnode.expressionnode.LiteralValue

class PrintStatementNode(
    override val type: String,
    override val location: Location,
    val value: ExpressionNode
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor): LiteralValue? {
        return visitor.visitPrintStatementNode(this)
    }
}
