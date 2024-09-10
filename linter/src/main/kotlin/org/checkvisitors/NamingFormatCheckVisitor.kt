package org.checkvisitors

import org.astnode.ASTNode
import org.astnode.astnodevisitor.VisitorResult
import org.astnode.statementnode.VariableDeclarationNode

class NamingFormatCheckVisitor(private val patternName: String, private val pattern: String) : CheckVisitors {
    override val warnings: MutableList<String> = mutableListOf()

    override fun visit(node: ASTNode): VisitorResult {
        return when (node) {
            is VariableDeclarationNode -> visitVariableDeclarationNode(node)
            else -> VisitorResult.Empty
        }
    }

    private fun visitVariableDeclarationNode(node: VariableDeclarationNode): VisitorResult {
        // check that the variable declaration node's identifier is formated in the style of the pattern
        val patternMatch = node.identifier.name.matches(Regex(pattern))
        if (!patternMatch) {
            warnings.add(
                "Location:${node.identifier.location}," +
                    " Declaration's identifier '${node.identifier.name}' does not match the pattern $patternName"
            )
        }
        return VisitorResult.Empty
    }

    override fun checkWarnings(): VisitorResult {
        if (warnings.isNotEmpty()) {
            return VisitorResult.ListResult(warnings)
        }
        return VisitorResult.Empty
    }
}
