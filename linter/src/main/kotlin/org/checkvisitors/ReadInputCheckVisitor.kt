package org.checkvisitors

import org.astnode.ASTNode
import org.astnode.ProgramNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.VisitorResult
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.ReadInputNode

class ReadInputCheckVisitor(private val enabled: Boolean) : ASTNodeVisitor {
    private val warnings: MutableList<String> = mutableListOf()
    override fun visit(node: ASTNode): VisitorResult {
        return when (node) {
            is ProgramNode -> visitProgramNode(node)
            is ReadInputNode -> visitReadInputNode(node)
            else -> VisitorResult.Empty
        }
    }

    private fun visitProgramNode(node: ProgramNode): VisitorResult {
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

    private fun visitReadInputNode(node: ReadInputNode): VisitorResult {
        // si esta activado, debe ser un literal o un identificador la expresión dentro de readInput.
        if (enabled) {
            val isLiteralOrIdentifier = node.message is LiteralNode || node.message is IdentifierNode
            return if (isLiteralOrIdentifier) {
                VisitorResult.Empty
            } else {
                VisitorResult.ListResult(
                    listOf(
                        "Location:${node.location}, readInput message must be a variable or literal"
                    )
                )
            }
        }
        return VisitorResult.Empty
    }
}
