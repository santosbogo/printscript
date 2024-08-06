package org.parser.astnode.expressionnode

import org.utils.Location
import org.parser.astnode.astnodevisitor.ASTNodeVisitor

class Literal(
    override val type: String,
    override val location: Location,
    val value: LiteralValue
) : ExpressionNode {
    override fun accept(visitor: ASTNodeVisitor) {
        TODO("Not yet implemented")
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
