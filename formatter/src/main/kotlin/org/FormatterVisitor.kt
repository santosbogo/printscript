package org

import org.common.astnode.ASTNode
import org.common.astnode.ProgramNode
import org.common.astnode.astnodevisitor.ASTNodeVisitor
import org.common.astnode.astnodevisitor.types.VisitorResult
import org.common.astnode.expressionnode.BinaryExpressionNode
import org.common.astnode.expressionnode.IdentifierNode
import org.common.astnode.expressionnode.LiteralNode
import org.common.astnode.statementnode.AssignmentNode
import org.common.astnode.statementnode.PrintStatementNode
import org.common.astnode.statementnode.VariableDeclarationNode

class FormatterVisitor: ASTNodeVisitor {

    override fun visit(node: ASTNode): VisitorResult {
        return when (node) {
            is ProgramNode -> visitProgramNode(node)
            is AssignmentNode -> visitAssignmentNode(node)
            is PrintStatementNode -> visitPrintStatementNode(node)
            is VariableDeclarationNode -> visitVariableDeclarationNode(node)
            is LiteralNode -> visitLiteralNode(node)
            is IdentifierNode -> visitIdentifierNode(node)
            is BinaryExpressionNode -> visitBinaryExpressionNode(node)
            else -> throw UnsupportedOperationException("Unsupported node: ${node::class}")
        }
    }

    override fun visitProgramNode(node: ProgramNode): VisitorResult {
        TODO("Not yet implemented")
    }

    override fun visitAssignmentNode(node: AssignmentNode): VisitorResult {
        TODO("Not yet implemented")
    }

    override fun visitPrintStatementNode(node: PrintStatementNode): VisitorResult {
        TODO("Not yet implemented")
    }

    override fun visitVariableDeclarationNode(node: VariableDeclarationNode): VisitorResult {
        TODO("Not yet implemented")
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
}