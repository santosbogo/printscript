package org.shared.astnode.expressionnode.expressionnodevisitor

import org.shared.astnode.ProgramNode
import org.shared.astnode.expressionnode.BinaryExpressionNode
import org.shared.astnode.expressionnode.IdentifierNode
import org.shared.astnode.expressionnode.LiteralNode
import org.shared.astnode.statementnode.AssignmentNode
import org.shared.astnode.statementnode.PrintStatementNode
import org.shared.astnode.statementnode.VariableDeclarationNode

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
