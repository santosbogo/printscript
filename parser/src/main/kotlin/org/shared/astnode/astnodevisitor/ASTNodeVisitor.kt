package org.shared.astnode.astnodevisitor

import org.shared.astnode.ProgramNode
import org.shared.astnode.expressionnode.BinaryExpressionNode
import org.shared.astnode.expressionnode.IdentifierNode
import org.shared.astnode.expressionnode.LiteralNode
import org.shared.astnode.statementnode.AssignmentNode
import org.shared.astnode.statementnode.PrintStatementNode
import org.shared.astnode.statementnode.VariableDeclarationNode

interface ASTNodeVisitor {
    fun visitProgramNode(node: ProgramNode)
    fun visitAssignmentNode(node: AssignmentNode)
    fun visitPrintStatementNode(node: PrintStatementNode)
    fun visitVariableDeclarationNode(node: VariableDeclarationNode)
    fun visitLiteralNode(node: LiteralNode)
    fun visitBinaryExpressionNode(node: BinaryExpressionNode)
    fun visitIdentifierNode(node: IdentifierNode)
}
