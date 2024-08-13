package org.shared.astnode.astnodevisitor

import org.common.astnode.ProgramNode
import org.shared.astnode.expressionnode.BinaryExpressionNode
import org.shared.astnode.expressionnode.IdentifierNode
import org.shared.astnode.expressionnode.LiteralNode
import org.shared.astnode.expressionnode.LiteralValue
import org.common.astnode.statementnode.AssignmentNode
import org.common.astnode.statementnode.PrintStatementNode
import org.common.astnode.statementnode.VariableDeclarationNode

interface ASTNodeVisitor {
    fun visitProgramNode(node: ProgramNode): LiteralValue?
    fun visitAssignmentNode(node: AssignmentNode): LiteralValue?
    fun visitPrintStatementNode(node: PrintStatementNode): LiteralValue?
    fun visitVariableDeclarationNode(node: VariableDeclarationNode): LiteralValue?
    fun visitLiteralNode(node: LiteralNode): LiteralValue
    fun visitBinaryExpressionNode(node: BinaryExpressionNode): LiteralValue
    fun visitIdentifierNode(node: IdentifierNode): LiteralValue
}
