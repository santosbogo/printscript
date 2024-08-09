package org

import org.shared.astnode.ASTNode
import org.shared.astnode.ProgramNode
import org.shared.astnode.astnodevisitor.ASTNodeVisitor
import org.shared.astnode.statementnode.AssignmentNode
import org.shared.astnode.statementnode.PrintStatementNode
import org.shared.astnode.statementnode.VariableDeclarationNode
import org.shared.astnode.expressionnode.BinaryExpressionNode
import org.shared.astnode.expressionnode.IdentifierNode
import org.shared.astnode.expressionnode.LiteralNode

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
