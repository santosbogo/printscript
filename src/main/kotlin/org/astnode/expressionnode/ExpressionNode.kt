package org.astnode.expressionnode

import org.Location
import org.astnode.ASTNode
import org.astnode.astnodevisitor.ASTNodeVisitor

interface ExpressionNode : ASTNode {
    override val type: String
    override val location: Location
    override fun accept(visitor: ASTNodeVisitor)
}
