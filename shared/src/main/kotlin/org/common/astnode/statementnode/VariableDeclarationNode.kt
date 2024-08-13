package org.common.astnode.statementnode

import org.common.Location
import org.shared.astnode.astnodevisitor.ASTNodeVisitor
import org.shared.astnode.expressionnode.ExpressionNode
import org.shared.astnode.expressionnode.IdentifierNode
import org.shared.astnode.expressionnode.LiteralValue

class VariableDeclarationNode(
    override val type: String,
    override val location: Location,
    val identifier: IdentifierNode,
    val init: ExpressionNode,
    private val kind: String
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor): Map<String, Any> {
        return visitor.visitVariableDeclarationNode(this)
    }
}
