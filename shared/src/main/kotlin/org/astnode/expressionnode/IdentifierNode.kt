package org.astnode.expressionnode

import org.Location
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.types.VisitorResult

class IdentifierNode(
    override val type: String,
    override val location: Location,
    val name: String,
    var dataType: String
) : ExpressionNode {
    override fun accept(visitor: ASTNodeVisitor): VisitorResult {
        return visitor.visit(this)
    }

    override fun getType(symbolTable: MutableMap<String, LiteralValue>): String {
        return symbolTable[name]?.getType() ?: dataType
    }
}
