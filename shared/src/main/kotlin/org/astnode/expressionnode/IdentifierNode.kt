package org.astnode.expressionnode

import org.Location
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.VisitorResult

class IdentifierNode(
    override val type: String,
    override val location: Location,
    val name: String,
    var dataType: String,
    val kind: String
) : ExpressionNode {
    override fun accept(visitor: ASTNodeVisitor): VisitorResult {
        return visitor.visit(this)
    }

    override fun getType(symbolTable: MutableMap<String, Pair<String, LiteralValue>>): String {
        return symbolTable[name]?.second?.getType() ?: dataType
    }

    override fun toString(): String {
        return name
    }
}
