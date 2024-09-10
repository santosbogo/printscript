package org.checkvisitors

import org.astnode.ASTNode
import org.astnode.astnodevisitor.VisitorResult
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.statementnode.AssignmentNode
import org.astnode.statementnode.PrintStatementNode
import org.astnode.statementnode.VariableDeclarationNode

class PrintUseCheckVisitor(private val enabled: Boolean) : CheckVisitors {
    override val warnings: MutableList<String> = mutableListOf()

    override fun checkWarnings(): VisitorResult {
        return if (warnings.isNotEmpty()) {
            VisitorResult.ListResult(warnings)
        } else {
            VisitorResult.Empty
        }
    }

    override fun visit(node: ASTNode): VisitorResult {
        return when (node) {
            is AssignmentNode -> visitAssignmentNode(node)
            is PrintStatementNode -> visitPrintStatementNode(node)
            is VariableDeclarationNode -> visitVariableDeclarationNode(node)
            else -> VisitorResult.Empty
        }
    }

    private fun visitAssignmentNode(node: AssignmentNode): VisitorResult {
        return VisitorResult.Empty
    }

    private fun visitPrintStatementNode(node: PrintStatementNode): VisitorResult {
        if (enabled && node.value is BinaryExpressionNode) {
            warnings.add(
                "Location:${node.location}, " +
                    "Print statement should be called as a variable or Literal."
            )
        }
        return VisitorResult.Empty
    }

    private fun visitVariableDeclarationNode(node: VariableDeclarationNode): VisitorResult {
        return VisitorResult.Empty
    }
}
