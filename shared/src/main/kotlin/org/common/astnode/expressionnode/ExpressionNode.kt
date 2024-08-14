package org.shared.astnode.expressionnode

import org.common.Location
import org.common.astnode.ASTNode
import org.common.astnode.astnodevisitor.ASTNodeVisitor
import org.common.astnode.astnodevisitor.types.VisitorResult

interface ExpressionNode : ASTNode {
    override val type: String
    override val location: Location
    override fun accept(visitor: ASTNodeVisitor): VisitorResult
}
