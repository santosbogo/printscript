package org.astnode.statementnode

import org.Location
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.expressionnode.ExpressionNode

class VariableDeclarationNode(
    override val type: String,
    override val location: Location,
    val identifier: String,
    val init: ExpressionNode,
    private val kind: String
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor) {
        visitor.visitVariableDeclarationNode(this)
    }
}
