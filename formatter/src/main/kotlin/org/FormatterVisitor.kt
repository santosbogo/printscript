package org

import org.astnode.ASTNode
import org.astnode.ProgramNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.VisitorResult
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.ExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.statementnode.AssignmentNode
import org.astnode.statementnode.PrintStatementNode
import org.astnode.statementnode.VariableDeclarationNode

class FormatterVisitor : ASTNodeVisitor {

    override fun visit(node: ASTNode): VisitorResult {
        return when (node) {
            is ProgramNode -> visitProgramNode(node)
            is AssignmentNode -> visitAssignmentNode(node)
            is PrintStatementNode -> visitPrintStatementNode(node)
            is VariableDeclarationNode -> visitVariableDeclarationNode(node)
            is LiteralNode -> visitLiteralNode(node)
            is BinaryExpressionNode -> visitBinaryExpressionNode(node)
            is IdentifierNode -> visitIdentifierNode(node)
            else -> VisitorResult.Empty
        }
    }

    private fun visitProgramNode(node: ProgramNode): VisitorResult {
        return VisitorResult.StringResult("We can't reach here")
    }

    private fun visitAssignmentNode(node: AssignmentNode): VisitorResult {
        val result = "${node.identifier.name} = ${getExpression(node.value)};"
        return VisitorResult.StringResult(result)
    }

    private fun visitPrintStatementNode(node: PrintStatementNode): VisitorResult {
        val result = "println(${getExpression(node.value)});"
        return VisitorResult.StringResult(result)
    }

    private fun visitVariableDeclarationNode(node: VariableDeclarationNode): VisitorResult {
        val result: String = node.kind + " " + node.identifier.name + ":" + node.identifier.dataType +
            "=" + getExpression(node.init) + ";"
        return VisitorResult.StringResult(result)
    }

    private fun visitLiteralNode(node: LiteralNode): VisitorResult {
        return VisitorResult.StringResult(node.value.toString() + ";")
    }

    private fun visitBinaryExpressionNode(node: BinaryExpressionNode): VisitorResult {
        val result: String = "${getExpression(node.left)} ${node.operator} ${getExpression(node.right)}" + ";"
        return VisitorResult.StringResult(result)
    }

    private fun visitIdentifierNode(node: IdentifierNode): VisitorResult {
        return VisitorResult.StringResult(node.name + ";")
    }

    private fun getExpression(init: ExpressionNode): String {
        return when (init) {
            is LiteralNode -> init.value.toString()
            is BinaryExpressionNode -> "${getExpression(init.left)} ${init.operator} ${getExpression(init.right)}"
            is IdentifierNode -> init.name
            else -> throw Exception("Unsupported expression")
        }
    }
}
