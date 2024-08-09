package org.shared.astnode.expressionnode

import org.shared.Location
import org.shared.astnode.astnodevisitor.ASTNodeVisitor

class LiteralNode(
    override val type: String,
    override val location: Location,
    val value: LiteralValue
) : ExpressionNode {
    override fun accept(visitor: ASTNodeVisitor) {
        visitor.visitLiteralNode(this)
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
