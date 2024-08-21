package org

import org.astnode.ProgramNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.types.VisitorResult
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.ExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.statementnode.AssignmentNode
import org.astnode.statementnode.PrintStatementNode
import org.astnode.statementnode.VariableDeclarationNode

class FormatterVisitor : ASTNodeVisitor {

    override fun visitProgramNode(node: ProgramNode): VisitorResult {
        return VisitorResult.StringResult("We can't reach here")
    }

    override fun visitAssignmentNode(node: AssignmentNode): VisitorResult {
        val result: String = "${node.identifierNode} = ${node.value}"
        return VisitorResult.StringResult(result)
    }

    override fun visitPrintStatementNode(node: PrintStatementNode): VisitorResult {
        val result: String = "println(${node.value})"
        return VisitorResult.StringResult(result)
    }

    override fun visitVariableDeclarationNode(node: VariableDeclarationNode): VisitorResult {
        val result: String = node.kind + " " + node.identifier.name + ":" + node.identifier.dataType + "=" + getExpression(node.init) + ";"
        return VisitorResult.StringResult(result)
    }

    override fun visitLiteralNode(node: LiteralNode): VisitorResult {
        return VisitorResult.StringResult(node.value.toString())
    }

    override fun visitBinaryExpressionNode(node: BinaryExpressionNode): VisitorResult {
        val result: String = "${getExpression(node.left)} ${node.operator} ${getExpression(node.right)}"
        return VisitorResult.StringResult(result)
    }

    override fun visitIdentifierNode(node: IdentifierNode): VisitorResult {
        return VisitorResult.StringResult(node.name)
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
