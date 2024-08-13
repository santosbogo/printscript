package org.common.astnode

import org.common.Location
import org.shared.astnode.ASTNode
import org.common.astnode.astnodevisitor.ASTNodeVisitor
import org.common.astnode.astnodevisitor.types.VisitorResult
import org.shared.astnode.expressionnode.LiteralValue

class ProgramNode(
    override val type: String,
    override val location: Location,
    val statements: List<ASTNode>
) : ASTNode {
    override fun accept(visitor: ASTNodeVisitor): VisitorResult {
        return visitor.visitProgramNode(this)
    }
}
