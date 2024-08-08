package org.parser.astnode.expressionnode

import org.Location
import org.parser.astnode.expressionnode.expressionnodevisitor.ExpressionNodeVisitor

class Literal(
    override val type: String,
    override val location: Location,
    val value: LiteralValue
) : ExpressionNode {
    override fun accept(visitor: ExpressionNodeVisitor): Any {
        return visitor.visitLiteral(this)
    }
}

sealed class LiteralValue {
    abstract fun getType(): String

    data class StringValue(val value: String) : LiteralValue() {
        override fun getType(): String = "String"
    }

    data class NumberValue(val value: Number) : LiteralValue() {
        override fun getType(): String = "Number"
    }
}
