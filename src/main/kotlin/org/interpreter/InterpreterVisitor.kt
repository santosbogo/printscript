package org.interpreter

import org.astnode.ASTNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.expressionnode.ExpressionNode
import org.astnode.expressionnode.Identifier
import org.astnode.expressionnode.Literal
import org.astnode.statementnode.AssignmentNode
import org.astnode.statementnode.PrintStatementNode
import org.astnode.statementnode.VariableDeclarationNode

class InterpreterVisitor(private val symbolTable: MutableMap<String, Any>) : ASTNodeVisitor {

    override fun visit(node: ASTNode) {
        when (node) {
            is AssignmentNode -> visitAssignmentNode(node)
            is PrintStatementNode -> visitPrintStatementNode(node)
            is VariableDeclarationNode -> visitVariableDeclarationNode(node)
            else -> throw UnsupportedOperationException("Node type not supported")
        }
    }

    private fun visitAssignmentNode(node: AssignmentNode) {
        val variableIdentifier = node.identifier
        val value = evaluateExpression(node.expression)
        symbolTable[variableIdentifier.name] = value
    }

    private fun visitPrintStatementNode(node: PrintStatementNode) {
        val value = evaluateExpression(node.expression)
        println(value)
    }

    private fun visitVariableDeclarationNode(node: VariableDeclarationNode) {
        val variableIdentifier = node.identifier
        val value = evaluateExpression(node.init)
        symbolTable[variableIdentifier] = value
    }

    private fun evaluateExpression(expression: ExpressionNode): Any {
        return when (expression) {
            is Literal -> expression.value
            is Identifier -> symbolTable[expression.name] ?: throw Exception("Undefined variable: ${expression.name}")
            else -> throw UnsupportedOperationException("Expression type not supported")
        }
    }
}
