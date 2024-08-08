package org.astnode.statementnode

import org.Location
import org.Token
import org.astnode.ASTNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.expressionnode.ExpressionNode

interface StatementNode : ASTNode {
    override val type: String
    override val location: Location
    val expression: ExpressionNode
    override fun accept(visitor: ASTNodeVisitor)
    fun generate(tokens: List<Token>): ASTNode
    val formula: List<String>
}
