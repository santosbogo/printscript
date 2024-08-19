package org.common.astnode.statementnode

import org.common.Location
import org.common.astnode.astnodevisitor.ASTNodeVisitor
import org.common.astnode.astnodevisitor.types.VisitorResult
import org.common.astnode.expressionnode.ExpressionNode
import org.common.astnode.expressionnode.IdentifierNode

class VariableDeclarationNode(
    override val type: String,
    override val location: Location,
    val identifier: IdentifierNode,
    val init: ExpressionNode,
    val kind: String
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor): VisitorResult {
        return visitor.visitVariableDeclarationNode(this)
    }
}
