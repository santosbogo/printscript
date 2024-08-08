package org.astnode.expressionnode.expressionnodevisitor

import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode

interface ExpressionNodeVisitor : ASTNodeVisitor {
    fun visitLiteral(node: LiteralNode): Any
    fun visitBinaryExpression(node: BinaryExpressionNode): Any
    fun visitIdentifier(node: IdentifierNode): Any
}
