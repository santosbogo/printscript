package org.checkvisitors

import org.astnode.ASTNode
import org.astnode.ProgramNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.VisitorResult
import org.astnode.statementnode.AssignmentNode
import org.astnode.statementnode.PrintStatementNode
import org.astnode.statementnode.VariableDeclarationNode

class NamingFormatCheckVisitor(private val patternName: String, private val pattern: String) : ASTNodeVisitor {
    private val warnings: MutableList<String> = mutableListOf()

    override fun visit(node: ASTNode): VisitorResult {
        return when (node) {
            is ProgramNode -> visitProgramNode(node)
            is AssignmentNode -> visitAssignmentNode(node)
            is PrintStatementNode -> visitPrintStatementNode(node)
            is VariableDeclarationNode -> visitVariableDeclarationNode(node)
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

    private fun visitAssignmentNode(node: AssignmentNode): VisitorResult {
        // check that the assignment node's identifier is formated in the style of the pattern
        val patternMatch = node.identifier.name.matches(Regex(pattern))
        if (!patternMatch) {
            return VisitorResult.ListResult(
                listOf(
                    "Location:${node.identifier.location}, Identifier '${node.identifier.name}'" +
                        " does not match the pattern $patternName"
                )
            )
        }
        return VisitorResult.Empty
    }

    private fun visitPrintStatementNode(node: PrintStatementNode): VisitorResult {
        return VisitorResult.Empty
    }

    private fun visitVariableDeclarationNode(node: VariableDeclarationNode): VisitorResult {
        // check that the variable declaration node's identifier is formated in the style of the pattern
        val patternMatch = node.identifier.name.matches(Regex(pattern))
        if (!patternMatch) {
            return VisitorResult.ListResult(
                listOf(
                    "Location:${node.identifier.location}," +
                        " Identifier '${node.identifier.name}' does not match the pattern $patternName"
                )
            )
        }
        return VisitorResult.Empty
    }
}
