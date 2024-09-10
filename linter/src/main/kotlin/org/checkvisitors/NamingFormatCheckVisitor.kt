package org.checkvisitors

import org.astnode.ASTNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.VisitorResult
import org.astnode.statementnode.AssignmentNode
import org.astnode.statementnode.VariableDeclarationNode

class NamingFormatCheckVisitor(private val patternName: String, private val pattern: String) : ASTNodeVisitor {

    override fun visit(node: ASTNode): VisitorResult {
        return when (node) {
            is AssignmentNode -> visitAssignmentNode(node)
            is VariableDeclarationNode -> visitVariableDeclarationNode(node)
            else -> VisitorResult.Empty
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
