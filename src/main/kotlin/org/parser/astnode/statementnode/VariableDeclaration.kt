package org.parser.astnode.statementnode

import org.Location
import org.parser.astnode.astnodevisitor.ASTNodeVisitor
import org.parser.astnode.expressionnode.ExpressionNode

class VariableDeclaration(
    override val type: String,
    override val location: Location,
    override val expression: ExpressionNode,
    private val identifier: String,
    private val init: ExpressionNode,
    private val kind: String
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor) {
        TODO("Not yet implemented")
    }
}