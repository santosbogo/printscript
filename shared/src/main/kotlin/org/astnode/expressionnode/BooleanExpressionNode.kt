package org.astnode.expressionnode

import org.Location
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.VisitorResult

class BooleanExpressionNode(
    override val type: String,
    override val location: Location,
    val bool: ExpressionNode
) : ExpressionNode {

    override fun accept(visitor: ASTNodeVisitor): VisitorResult {
        return visitor.visit(this)
    }

    override fun getType(symbolTable: MutableMap<String, Pair<String, LiteralValue>>): String {
        return bool.getType(symbolTable)
    }

    override fun toString(): String {
        return bool.toString()
    }
}
