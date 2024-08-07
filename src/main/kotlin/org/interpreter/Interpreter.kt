package org.interpreter

import org.parser.astnode.ASTNode
import org.parser.astnode.astnodevisitor.ASTNodeVisitor
import org.parser.astnode.expressionnode.ExpressionNode
import org.parser.astnode.expressionnode.Identifier
import org.parser.astnode.expressionnode.Literal
import org.parser.astnode.statementnode.AssignmentNode

class Interpreter : ASTNodeVisitor {

    private val symbolTable: MutableMap<String, Any> = mutableMapOf()

    fun interpret(node: ASTNode) {
        visit(node)
    }

    private fun visit(node: ASTNode) {
        when (node) {
            is AssignmentNode -> visitAssignmentNode(node)
            // Add other node types here
            else -> throw UnsupportedOperationException("Node type not supported")
        }
    }

    private fun visitAssignmentNode(node: AssignmentNode) {
        val variableIdentifier = node.identifier
        val value = evaluateExpression(node.expression)
        symbolTable[variableIdentifier.name] = value
    }

private fun evaluateExpression(expression: ExpressionNode): Any {
    return when (expression) {
        is Literal -> expression.value
        is Identifier -> symbolTable[expression.name] ?: throw Exception("Undefined variable: ${expression.name}")
        // Add other expression types here
        else -> throw UnsupportedOperationException("Expression type not supported")
    }
}
}