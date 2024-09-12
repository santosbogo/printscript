package org

import org.astnode.ASTNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.VisitorResult
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.BooleanExpressionNode
import org.astnode.expressionnode.ExpressionNode
import org.astnode.statementnode.AssignmentNode
import org.astnode.statementnode.CompleteIfNode
import org.astnode.statementnode.ElseNode
import org.astnode.statementnode.IfNode
import org.astnode.statementnode.PrintStatementNode
import org.astnode.statementnode.VariableDeclarationNode

class FormatterVisitor : ASTNodeVisitor {

    override fun visit(node: ASTNode): VisitorResult {
        return when (node) {
            is AssignmentNode -> visitAssignmentNode(node)
            is PrintStatementNode -> visitPrintStatementNode(node)
            is VariableDeclarationNode -> visitVariableDeclarationNode(node)
            is LiteralNode -> visitLiteralNode(node)
            is BinaryExpressionNode -> visitBinaryExpressionNode(node)
            is IdentifierNode -> visitIdentifierNode(node)
            is IfNode -> visitIfNode(node)
            is ElseNode -> visitElseNode(node)
            is CompleteIfNode -> visitCompleteIfNode(node)
            else -> VisitorResult.Empty
        }
    }

    private fun visitCompleteIfNode(node: CompleteIfNode): VisitorResult {
        val ifNode = node.ifNode
        val elseNode = node.elseNode
        val result = visitIfNode(ifNode).toString() +
            elseNode?.let { visitElseNode(it) }.toString()
        return VisitorResult.StringResult(result)
    }

    private fun visitIfNode(node: IfNode): VisitorResult {
        val result = "if (${getExpression(node.boolean)})" +
            " {" + getStatements(node.ifStatements) + "}"
        return VisitorResult.StringResult(result)
    }

    private fun visitElseNode(node: ElseNode): VisitorResult {
        val result = " else {" + getStatements(node.elseStatements) + "}"
        return VisitorResult.StringResult(result)
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
        val result: String = node.kind + " " + node.identifier.name +
            ":" + node.identifier.dataType +
            "=" + getExpression(node.init) + ";"
        return VisitorResult.StringResult(result)
    }

    private fun visitLiteralNode(node: LiteralNode): VisitorResult {
        return VisitorResult.StringResult(node.value.toString() + ";")
    }

    private fun visitBinaryExpressionNode(node: BinaryExpressionNode): VisitorResult {
        val result: String = "${getExpression(node.left)} " +
            "${node.operator} ${getExpression(node.right)}" + ";"
        return VisitorResult.StringResult(result)
    }

    private fun visitIdentifierNode(node: IdentifierNode): VisitorResult {
        return VisitorResult.StringResult(node.name + ";")
    }

    private fun getExpression(init: ExpressionNode): String {
        return when (init) {
            is LiteralNode -> {
                if (init.value is LiteralValue.StringValue) {
                    "\"${init.value}\""
                } else {
                    init.value.toString()
                }
            }
            is BinaryExpressionNode -> "${getExpression(init.left)} " +
                "${init.operator} ${getExpression(init.right)}"
            is IdentifierNode -> init.name
            is BooleanExpressionNode -> getExpression(init.bool)
            else -> throw Exception("Unsupported expression")
        }
    }

    private fun getStatements(nodes: List<ASTNode>): String {
        var result = ""
        nodes.forEach {
            result += visit(it).toString()
        }
        return result
    }
}
