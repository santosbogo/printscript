package org.shared.astnode.expressionnode.expressionnodevisitor

import org.shared.astnode.astnodevisitor.ASTNodeVisitor
import org.shared.astnode.expressionnode.BinaryExpressionNode
import org.shared.astnode.expressionnode.IdentifierNode
import org.shared.astnode.expressionnode.LiteralNode

interface ExpressionNodeVisitor : ASTNodeVisitor {
    fun visitLiteral(node: LiteralNode): Any
    fun visitBinaryExpression(node: BinaryExpressionNode): Any
    fun visitIdentifier(node: IdentifierNode): Any
}
