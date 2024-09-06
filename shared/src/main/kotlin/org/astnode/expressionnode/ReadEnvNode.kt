package org.astnode.expressionnode

import org.Location
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.VisitorResult

class ReadEnvNode(
    override val type: String,
    override val location: Location,
    val variableName: String
) : ExpressionNode {
    override fun accept(visitor: ASTNodeVisitor): VisitorResult {
        return visitor.visit(this)
    }

    override fun getType(symbolTable: MutableMap<String, Pair<String, LiteralValue>>): String {
        return "Undefined"
    }

    override fun toString(): String {
        return "readEnv($variableName)"
    }
}
