package org.astnode.expressionnode

import org.Location
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.types.VisitorResult

class LiteralNode(
    override val type: String,
    override val location: Location,
    val value: LiteralValue
) : ExpressionNode {
    override fun accept(visitor: ASTNodeVisitor): VisitorResult {
        return visitor.visit(this)
    }

    override fun getType(symbolTable: MutableMap<String, LiteralValue>): String {
        return value.getType()
    }
}

sealed class LiteralValue {
    abstract fun getType(): String

    data class StringValue(val value: String) : LiteralValue() {
        override fun getType(): String = "string"
        override fun toString(): String {
            return value
        }
    }

    data class NumberValue(val value: Number) : LiteralValue() {
        override fun getType(): String = "number"
        override fun toString(): String {
            return value.toString()
        }
    }
}
