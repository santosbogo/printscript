package org.astnode.astnodevisitor

import org.astnode.ProgramNode
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.statementnode.AssignmentNode
import org.astnode.statementnode.PrintStatementNode
import org.astnode.statementnode.VariableDeclarationNode

interface ASTNodeVisitor {
    fun visitProgramNode(node: ProgramNode)
    fun visitAssignmentNode(node: AssignmentNode)
    fun visitPrintStatementNode(node: PrintStatementNode)
    fun visitVariableDeclarationNode(node: VariableDeclarationNode)
    fun visitLiteralNode(node: LiteralNode)
    fun visitBinaryExpressionNode(node: BinaryExpressionNode)
    fun visitIdentifierNode(node: IdentifierNode)
}
