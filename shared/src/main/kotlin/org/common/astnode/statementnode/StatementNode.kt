package org.shared.astnode.statementnode

import org.shared.Location
import org.shared.astnode.ASTNode
import org.shared.astnode.astnodevisitor.ASTNodeVisitor
import org.shared.astnode.expressionnode.LiteralValue

interface StatementNode : ASTNode {
    override val type: String
    override val location: Location
    override fun accept(visitor: ASTNodeVisitor): LiteralValue?
}
