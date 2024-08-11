package org.shared.astnode

import org.shared.Location
import org.shared.astnode.astnodevisitor.ASTNodeVisitor

class ProgramNode(
    override val type: String,
    override val location: Location,
    val statements: List<ASTNode>
) : ASTNode {
    override fun accept(visitor: ASTNodeVisitor) {
        visitor.visitProgramNode(this)
    }
}
