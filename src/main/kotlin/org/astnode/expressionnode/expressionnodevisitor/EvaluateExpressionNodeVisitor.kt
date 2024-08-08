package org.astnode.expressionnode.expressionnodevisitor

import org.astnode.ProgramNode
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.statementnode.AssignmentNode
import org.astnode.statementnode.PrintStatementNode
import org.astnode.statementnode.VariableDeclarationNode

class EvaluateExpressionNodeVisitor : ExpressionNodeVisitor {
    override fun visitLiteral(node: LiteralNode): Any {
        return node.value
    }

    override fun visitBinaryExpression(node: BinaryExpressionNode): Any {
        return node.left.accept(this) as Int + node.right.accept(this) as Int
    }

    override fun visitIdentifier(node: IdentifierNode): Any {
        return node.name
    }

    override fun visitProgramNode(node: ProgramNode) {
        TODO("Not yet implemented")
    }

    override fun visitAssignmentNode(node: AssignmentNode) {
        TODO("Not yet implemented")
    }

    override fun visitPrintStatementNode(node: PrintStatementNode) {
        TODO("Not yet implemented")
    }

    override fun visitVariableDeclarationNode(node: VariableDeclarationNode) {
        TODO("Not yet implemented")
    }

    override fun visitLiteralNode(node: LiteralNode) {
        TODO("Not yet implemented")
    }

    override fun visitBinaryExpressionNode(node: BinaryExpressionNode) {
        TODO("Not yet implemented")
    }

    override fun visitIdentifierNode(node: IdentifierNode) {
        TODO("Not yet implemented")
    }
}
