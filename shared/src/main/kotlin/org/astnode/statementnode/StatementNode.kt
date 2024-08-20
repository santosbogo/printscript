package org.astnode.statementnode

import org.Location
import org.astnode.ASTNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.types.VisitorResult

interface StatementNode : ASTNode {
    override val type: String
    override val location: Location
    override fun accept(visitor: ASTNodeVisitor): VisitorResult
}
