package org.checkvisitors

import org.astnode.ASTNode
import org.astnode.astnodevisitor.VisitorResult
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.ReadInputNode
import org.astnode.statementnode.AssignmentNode
import org.astnode.statementnode.PrintStatementNode
import org.astnode.statementnode.VariableDeclarationNode

class ReadInputCheckVisitor(private val enabled: Boolean) : CheckVisitors {
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
            is ReadInputNode -> visitReadInputNode(node)
            else -> VisitorResult.Empty
        }
    }

    private fun visitAssignmentNode(node: AssignmentNode): VisitorResult {
        if (enabled) {
            if (node.value is ReadInputNode) {
                return node.value.accept(this)
            }
        }
        return VisitorResult.Empty
    }

    private fun visitPrintStatementNode(node: PrintStatementNode): VisitorResult {
        if (enabled) {
            if (node.value is ReadInputNode) {
                return node.value.accept(this)
            }
        }
        return VisitorResult.Empty
    }

    private fun visitVariableDeclarationNode(node: VariableDeclarationNode): VisitorResult {
        if (enabled) {
            if (node.init is ReadInputNode) {
                return node.init.accept(this)
            }
        }
        return VisitorResult.Empty
    }

    private fun visitReadInputNode(node: ReadInputNode): VisitorResult {
        // Si está activado, debe ser un literal o un identificador la expresión dentro de readInput.
        if (enabled) {
            val isLiteralOrIdentifier = node.message is LiteralNode || node.message is IdentifierNode
            if (!isLiteralOrIdentifier) {
                warnings.add(
                    "Location:${node.location}, readInput message must be a variable or literal"
                )
            }
        }

        return VisitorResult.Empty
    }
}
