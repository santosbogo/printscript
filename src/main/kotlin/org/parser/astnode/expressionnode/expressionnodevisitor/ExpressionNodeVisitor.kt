package org.parser.astnode.expressionnode.expressionnodevisitor

import org.parser.astnode.expressionnode.BinaryExpression
import org.parser.astnode.expressionnode.ExpressionNode
import org.parser.astnode.expressionnode.Identifier
import org.parser.astnode.expressionnode.Literal

interface ExpressionNodeVisitor {
    fun visitLiteral(node: Literal): Any
    fun visitBinaryExpression(node: BinaryExpression): Any
    fun visitIdentifier(node: Identifier): Any

}