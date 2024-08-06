package org.parser.astnode.statementnode

import org.Location
import org.parser.astnode.astnodevisitor.ASTNodeVisitor
import org.parser.astnode.expressionnode.ExpressionNode
import org.parser.astnode.expressionnode.Identifier

class AssignmentNode(
    override val type: String,
    override val location: Location,
    override val expression: ExpressionNode,
    val identifier: String
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor) {
        TODO("Not yet implemented")
    }
}