package org.common.astnode.astnodevisitor

import org.common.astnode.ProgramNode
import org.common.astnode.astnodevisitor.types.VisitorResult
import org.common.astnode.expressionnode.BinaryExpressionNode
import org.common.astnode.expressionnode.IdentifierNode
import org.common.astnode.expressionnode.LiteralNode
import org.common.astnode.statementnode.AssignmentNode
import org.common.astnode.statementnode.PrintStatementNode
import org.common.astnode.statementnode.VariableDeclarationNode
import org.common.astnode.ASTNode

interface ASTNodeVisitor {
    val symbolTable: Map<String, Any>
    fun visit(node: ASTNode): Any
    fun visitProgramNode(node: ProgramNode): VisitorResult
    fun visitAssignmentNode(node: AssignmentNode): VisitorResult
    fun visitPrintStatementNode(node: PrintStatementNode): VisitorResult
    fun visitVariableDeclarationNode(node: VariableDeclarationNode): VisitorResult
    fun visitLiteralNode(node: LiteralNode): VisitorResult
    fun visitBinaryExpressionNode(node: BinaryExpressionNode): VisitorResult
    fun visitIdentifierNode(node: IdentifierNode): VisitorResult
}
