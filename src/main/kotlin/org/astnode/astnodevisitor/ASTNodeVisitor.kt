package org.astnode.astnodevisitor

import org.astnode.ProgramNode
import org.astnode.statementnode.AssignmentNode
import org.astnode.statementnode.PrintStatementNode
import org.astnode.statementnode.VariableDeclarationNode

interface ASTNodeVisitor {
    fun visitProgramNode(node: ProgramNode)
    fun visitAssignmentNode(node: AssignmentNode)
    fun visitPrintStatementNode(node: PrintStatementNode)
    fun visitVariableDeclarationNode(node: VariableDeclarationNode)
}
