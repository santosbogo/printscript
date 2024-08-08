package org.interpreter

import org.astnode.ASTNode
import org.astnode.ProgramNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.expressionnode.*
import org.astnode.statementnode.AssignmentNode
import org.astnode.statementnode.PrintStatementNode
import org.astnode.statementnode.VariableDeclarationNode

class InterpreterVisitor(private val symbolTable: MutableMap<String, Any>) : ASTNodeVisitor {

    fun visit(node: ASTNode) {
        when (node) {
            is AssignmentNode -> visitAssignmentNode(node)
            is PrintStatementNode -> visitPrintStatementNode(node)
            is VariableDeclarationNode -> visitVariableDeclarationNode(node)
            else -> throw UnsupportedOperationException("Node type not supported")
        }
    }

    override fun visitProgramNode(node: ProgramNode) {
        val statements = node.statements
        statements.forEach { it.accept(this) }
    }

    override fun visitAssignmentNode(node: AssignmentNode) {
        val variableIdentifier = node.identifierNode
        val value = node.value.accept(this)
        symbolTable[variableIdentifier.name] = value
    }

    override fun visitPrintStatementNode(node: PrintStatementNode) {
        val value = node.value.accept(this)
        println(value)
    }

    override fun visitVariableDeclarationNode(node: VariableDeclarationNode) {
        val variableIdentifier = node.identifier
        val value = node.init.accept(this)
        symbolTable[variableIdentifier] = value
    }

    override fun visitLiteralNode(node: LiteralNode) {
        throw UnsupportedOperationException("Literal node not supported")
    }

    override fun visitIdentifierNode(node: IdentifierNode) {
        throw UnsupportedOperationException("Identifier node not supported")
    }

    override fun visitBinaryExpressionNode(node: BinaryExpressionNode) {
        throw UnsupportedOperationException("Binary expression node not supported")
    }
}
