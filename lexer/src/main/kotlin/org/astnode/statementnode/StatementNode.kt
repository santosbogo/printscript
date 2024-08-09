package org.astnode.statementnode

import org.Location
import org.astnode.ASTNode
import org.astnode.astnodevisitor.ASTNodeVisitor

interface StatementNode : ASTNode {
    override val type: String
    override val location: Location
    override fun accept(visitor: ASTNodeVisitor)
}
