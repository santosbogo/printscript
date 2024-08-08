package org.astnode

import org.Location
import org.astnode.astnodevisitor.ASTNodeVisitor

class ProgramNode(
    override val type: String,
    override val location: Location,
    val statements: List<ASTNode>
) : ASTNode {
    override fun accept(visitor: ASTNodeVisitor) {
        visitor.visit(this)
    }
}
