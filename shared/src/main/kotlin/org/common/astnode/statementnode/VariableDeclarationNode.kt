package org.shared.astnode.statementnode

import org.shared.Location
import org.shared.astnode.astnodevisitor.ASTNodeVisitor
import org.shared.astnode.expressionnode.ExpressionNode
import org.shared.astnode.expressionnode.IdentifierNode

class VariableDeclarationNode(
    override val type: String,
    override val location: Location,
    val identifier: IdentifierNode,
    val init: ExpressionNode,
    private val kind: String
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor) {
        visitor.visitVariableDeclarationNode(this)
    }
}
