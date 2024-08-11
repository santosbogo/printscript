package org.shared.astnode

import org.shared.Location
import org.shared.astnode.astnodevisitor.ASTNodeVisitor
import org.shared.astnode.expressionnode.LiteralValue

class ProgramNode(
    override val type: String,
    override val location: Location,
    val statements: List<ASTNode>
) : ASTNode {
    override fun accept(visitor: ASTNodeVisitor): LiteralValue? {
        return visitor.visitProgramNode(this)
    }
}
