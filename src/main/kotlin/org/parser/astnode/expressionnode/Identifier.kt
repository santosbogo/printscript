package org.parser.astnode.expressionnode

import org.utils.Location
import org.parser.astnode.astnodevisitor.ASTNodeVisitor

class Identifier(
    override val type: String,
    override val location: Location,
    val name: String,
    val dataType: String
) : ExpressionNode {
    override fun accept(visitor: ASTNodeVisitor) {
        TODO("Not yet implemented")
    }
}