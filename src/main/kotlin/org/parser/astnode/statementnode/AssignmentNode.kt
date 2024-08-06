package org.parser.astnode.statementnode

import org.utils.Location
import org.parser.astnode.astnodevisitor.ASTNodeVisitor
import org.parser.astnode.expressionnode.ExpressionNode

class AssignmentNode(
    override val type: String,
    override val location: Location,
    override val expression: ExpressionNode
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor) {
        TODO("Not yet implemented")
    }
}