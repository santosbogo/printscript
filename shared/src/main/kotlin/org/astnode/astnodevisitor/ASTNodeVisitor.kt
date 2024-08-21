package org.astnode.astnodevisitor

import org.astnode.ASTNode
import org.astnode.ProgramNode
import org.astnode.astnodevisitor.types.VisitorResult
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.statementnode.AssignmentNode
import org.astnode.statementnode.PrintStatementNode
import org.astnode.statementnode.VariableDeclarationNode

interface ASTNodeVisitor {
    fun visitProgramNode(node: ProgramNode): VisitorResult
    fun visitAssignmentNode(node: AssignmentNode): VisitorResult
    fun visitPrintStatementNode(node: PrintStatementNode): VisitorResult
    fun visitVariableDeclarationNode(node: VariableDeclarationNode): VisitorResult
    fun visitLiteralNode(node: LiteralNode): VisitorResult
    fun visitBinaryExpressionNode(node: BinaryExpressionNode): VisitorResult
    fun visitIdentifierNode(node: IdentifierNode): VisitorResult
    fun visit(node: ASTNode): VisitorResult {
        return when (node) {
            is ProgramNode -> visitProgramNode(node)
            is AssignmentNode -> visitAssignmentNode(node)
            is PrintStatementNode -> visitPrintStatementNode(node)
            is VariableDeclarationNode -> visitVariableDeclarationNode(node)
            is LiteralNode -> visitLiteralNode(node)
            is IdentifierNode -> visitIdentifierNode(node)
            is BinaryExpressionNode -> visitBinaryExpressionNode(node)
            else -> throw UnsupportedOperationException("Unsupported node: ${node::class}")
        }
    }
}
