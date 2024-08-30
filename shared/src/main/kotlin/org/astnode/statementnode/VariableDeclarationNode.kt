package org.astnode.statementnode

import org.Location
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.types.VisitorResult
import org.astnode.expressionnode.ExpressionNode
import org.astnode.expressionnode.IdentifierNode

class VariableDeclarationNode(
    override val type: String,
    override val location: Location,
    val identifier: IdentifierNode,
    val init: ExpressionNode,
    val kind: String
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor): VisitorResult {
        return visitor.visit(this)
    }
}
