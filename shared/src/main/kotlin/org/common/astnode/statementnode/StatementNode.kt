package org.shared.astnode.statementnode

import org.shared.Location
import org.shared.astnode.ASTNode
import org.shared.astnode.astnodevisitor.ASTNodeVisitor

interface StatementNode : ASTNode {
    override val type: String
    override val location: Location
    override fun accept(visitor: ASTNodeVisitor)
}
