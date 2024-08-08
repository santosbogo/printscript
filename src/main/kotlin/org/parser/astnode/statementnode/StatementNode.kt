package org.parser.astnode.statementnode

import org.Location
import org.lexer.Token
import org.parser.astnode.ASTNode
import org.parser.astnode.astnodevisitor.ASTNodeVisitor
import org.parser.astnode.expressionnode.ExpressionNode

interface StatementNode: ASTNode {
    override val type: String
    override val location: Location
    val expression: ExpressionNode
    override fun accept(visitor: ASTNodeVisitor)
    fun generate(tokens : List<Token>) : ASTNode
    val formula : List<String>
}