package org.shared.astnode.astnodevisitor

import org.common.astnode.ProgramNode
import org.shared.astnode.expressionnode.BinaryExpressionNode
import org.shared.astnode.expressionnode.IdentifierNode
import org.shared.astnode.expressionnode.LiteralNode
import org.shared.astnode.expressionnode.LiteralValue
import org.common.astnode.statementnode.AssignmentNode
import org.common.astnode.statementnode.PrintStatementNode
import org.common.astnode.statementnode.VariableDeclarationNode
import org.shared.astnode.ASTNode

interface ASTNodeVisitor {
    val symbolTable: Map<String, Any>
    fun visit(node: ASTNode): Any
    fun visitProgramNode(node: ProgramNode): Map<String, Any>
    fun visitAssignmentNode(node: AssignmentNode): Map<String, Any>
    fun visitPrintStatementNode(node: PrintStatementNode)
    fun visitVariableDeclarationNode(node: VariableDeclarationNode): Map<String, Any>
    fun visitLiteralNode(node: LiteralNode): LiteralValue
    fun visitBinaryExpressionNode(node: BinaryExpressionNode): LiteralValue
    fun visitIdentifierNode(node: IdentifierNode): LiteralValue
}
