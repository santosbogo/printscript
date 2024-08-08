package org.astnode.expressionnode.expressionnodevisitor

import org.astnode.expressionnode.BinaryExpression
import org.astnode.expressionnode.Identifier
import org.astnode.expressionnode.Literal

interface ExpressionNodeVisitor {
    fun visitLiteral(node: Literal): Any
    fun visitBinaryExpression(node: BinaryExpression): Any
    fun visitIdentifier(node: Identifier): Any
}
