package org.checkvisitors

import org.common.astnode.ProgramNode
import org.common.astnode.astnodevisitor.ASTNodeVisitor
import org.common.astnode.astnodevisitor.types.VisitorResult
import org.common.astnode.expressionnode.BinaryExpressionNode
import org.common.astnode.expressionnode.IdentifierNode
import org.common.astnode.expressionnode.LiteralNode
import org.common.astnode.statementnode.AssignmentNode
import org.common.astnode.statementnode.PrintStatementNode
import org.common.astnode.statementnode.VariableDeclarationNode

class PrintUseCheckVisitor(private val enabled: Boolean) : ASTNodeVisitor {
    private val warnings: MutableList<String> = mutableListOf()
    override fun visitProgramNode(node: ProgramNode): VisitorResult {
        val statements = node.statements
        statements.forEach {
            val result = it.accept(this)

            // si se devolvió un warning, lo agrego a la lista de warnings que despues voy a querer devolver.
            if (result.errors.isNotEmpty()) {
                warnings.addAll(result.errors) //agarro warnings.
            }
        }
        return VisitorResult(null, emptyMap(), warnings)
    }

    override fun visitAssignmentNode(node: AssignmentNode): VisitorResult {
        return VisitorResult(null, emptyMap())
    }

    override fun visitPrintStatementNode(node: PrintStatementNode): VisitorResult {
        if (enabled && node.value is BinaryExpressionNode){
            return VisitorResult(null, emptyMap(), listOf("Location:${node.location}, Print statement should be called with ID or Literal."))
        }
        return VisitorResult(null, emptyMap())

    }

    override fun visitVariableDeclarationNode(node: VariableDeclarationNode): VisitorResult {
        return VisitorResult(null, emptyMap())
    }

    override fun visitLiteralNode(node: LiteralNode): VisitorResult {
        return VisitorResult(null, emptyMap())
    }

    override fun visitBinaryExpressionNode(node: BinaryExpressionNode): VisitorResult {
        return VisitorResult(null, emptyMap())
    }

    override fun visitIdentifierNode(node: IdentifierNode): VisitorResult {
        return VisitorResult(null, emptyMap())
    }
}