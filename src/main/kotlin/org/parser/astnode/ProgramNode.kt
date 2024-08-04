package org.parser.astnode

import org.Location
import org.parser.astnode.astnodevisitor.ASTNodeVisitor

class ProgramNode(
    override val type: String,
    override val location: Location,
    val statements: List<ASTNode>
) : ASTNode {
    override fun accept(visitor: ASTNodeVisitor) {
        TODO("Not yet implemented")
    }
}