package org.astnode.expressionnode

import org.Location
import org.astnode.ASTNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.types.VisitorResult

interface ExpressionNode : ASTNode {
    override val type: String
    override val location: Location
    override fun accept(visitor: ASTNodeVisitor): VisitorResult
    fun getType(symbolTable: MutableMap<String, LiteralValue>): String
}
