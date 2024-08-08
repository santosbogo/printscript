package org.astnode.expressionnode.expressionnodevisitor

import org.astnode.expressionnode.BinaryExpression
import org.astnode.expressionnode.Identifier
import org.astnode.expressionnode.Literal

class EvaluateExpressionNodeVisitor : ExpressionNodeVisitor {
    override fun visitLiteral(node: Literal): Any {
        return node.value
    }

    override fun visitBinaryExpression(node: BinaryExpression): Any {
        return node.left.accept(this) as Int + node.right.accept(this) as Int
    }

    override fun visitIdentifier(node: Identifier): Any {
        return node.name
    }
}
