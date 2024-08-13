package org.common.astnode

import org.common.Location
import org.shared.astnode.ASTNode
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
