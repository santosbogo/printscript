package org.checkvisitors

import org.astnode.ProgramNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.types.VisitorResult
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.statementnode.AssignmentNode
import org.astnode.statementnode.PrintStatementNode
import org.astnode.statementnode.VariableDeclarationNode

class PrintUseCheckVisitor(private val enabled: Boolean) : ASTNodeVisitor {
    private val warnings: MutableList<String> = mutableListOf()
    override fun visitProgramNode(node: ProgramNode): VisitorResult {
        val statements = node.statements
        statements.forEach {
            val result = it.accept(this)

            // si se devolvió un warning, lo agrego a la lista de warnings que despues voy a querer devolver.
            if (result is VisitorResult.ListResult && result.value.isNotEmpty()) {
                warnings.addAll(result.value) // agarro warnings, el value es la lista.
            }
        }

        return if (warnings.isNotEmpty()) {
            VisitorResult.ListResult(warnings)
        } else {
            VisitorResult.Empty
        }
    }

    override fun visitAssignmentNode(node: AssignmentNode): VisitorResult {
        return VisitorResult.Empty
    }

    override fun visitPrintStatementNode(node: PrintStatementNode): VisitorResult {
        if (enabled && node.value is BinaryExpressionNode) {
            return VisitorResult.ListResult(
                listOf(
                    "Location:${node.location}, " +
                        "Print statement should be called with ID or Literal."
                )
            )
        }
        return VisitorResult.Empty
    }

    override fun visitVariableDeclarationNode(node: VariableDeclarationNode): VisitorResult {
        return VisitorResult.Empty
    }

    override fun visitLiteralNode(node: LiteralNode): VisitorResult {
        return VisitorResult.Empty
    }

    override fun visitBinaryExpressionNode(node: BinaryExpressionNode): VisitorResult {
        return VisitorResult.Empty
    }

    override fun visitIdentifierNode(node: IdentifierNode): VisitorResult {
        return VisitorResult.Empty
    }
}
