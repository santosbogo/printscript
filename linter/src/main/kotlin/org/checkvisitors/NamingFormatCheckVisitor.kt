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

class NamingFormatCheckVisitor(private val patternName: String, private val pattern: String) : ASTNodeVisitor {
    private val warnings: MutableList<String> = mutableListOf()

    override fun visitProgramNode(node: ProgramNode): VisitorResult {
        val statements = node.statements
        statements.forEach {
            val result = it.accept(this) as VisitorResult.ListResult

            // si se devolvió un warning, lo agrego a la lista de warnings que despues voy a querer devolver.
            if (result.value.isNotEmpty()) {
                warnings.addAll(result.value) // agarro warnings, el value es la lista.
            }
        }
        return VisitorResult.ListResult(warnings)
    }

    override fun visitAssignmentNode(node: AssignmentNode): VisitorResult {
        // check that the assignment node's identifier is formated in the style of the pattern
        val patternMatch = node.identifierNode.name.matches(Regex(pattern))
        if (!patternMatch) {
            return VisitorResult.ListResult(listOf("Location:${node.location}, Identifier ${node.identifierNode.name} does not match the pattern $patternName"))
        }
        return VisitorResult.Empty
    }

    override fun visitPrintStatementNode(node: PrintStatementNode): VisitorResult {
        return VisitorResult.Empty
    }

    override fun visitVariableDeclarationNode(node: VariableDeclarationNode): VisitorResult {
        // check that the variable declaration node's identifier is formated in the style of the pattern
        val patternMatch = node.identifier.name.matches(Regex(pattern))
        if (!patternMatch) {
            return VisitorResult.ListResult(listOf("Location:${node.location}, Identifier ${node.identifier.name} does not match the pattern $patternName"))
        }
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
