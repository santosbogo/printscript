package org.astnode.statementnode

import org.Location
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.VisitorResult
import org.astnode.expressionnode.ExpressionNode
import org.astnode.expressionnode.LiteralValue

class IfNode(
    override val type: String,
    override val location: Location,
    val boolean: LiteralValue,
    val ifExpression: ExpressionNode,
    val elseExpression: ExpressionNode? = null
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor): VisitorResult {
        return visitor.visit(this)
    }
}
