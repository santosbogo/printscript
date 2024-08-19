package org

import org.common.astnode.ProgramNode
import org.common.astnode.astnodevisitor.ASTNodeVisitor
import org.common.astnode.astnodevisitor.types.VisitorResult
import org.common.astnode.expressionnode.*
import org.common.astnode.statementnode.AssignmentNode
import org.common.astnode.statementnode.PrintStatementNode
import org.common.astnode.statementnode.VariableDeclarationNode

class FormatterVisitor: ASTNodeVisitor {
    
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
        val result: String = node.kind + " " + node.identifier.name + ": " + node.identifier.dataType + " = " + getExpression(node.init)
        return VisitorResult.StringResult(result)
    }

    override fun visitLiteralNode(node: LiteralNode): VisitorResult {
        TODO("Not yet implemented")
    }

    override fun visitBinaryExpressionNode(node: BinaryExpressionNode): VisitorResult {
        TODO("Not yet implemented")
    }

    override fun visitIdentifierNode(node: IdentifierNode): VisitorResult {
        TODO("Not yet implemented")
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