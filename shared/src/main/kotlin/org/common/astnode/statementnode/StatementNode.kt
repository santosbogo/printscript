package org.common.astnode.statementnode

import org.common.Location
import org.common.astnode.ASTNode
import org.common.astnode.astnodevisitor.ASTNodeVisitor
import org.common.astnode.astnodevisitor.types.VisitorResult

interface StatementNode : ASTNode {
    override val type: String
    override val location: Location
    override fun accept(visitor: ASTNodeVisitor): VisitorResult
}
