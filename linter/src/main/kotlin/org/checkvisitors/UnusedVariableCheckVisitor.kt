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

class UnusedVariableCheckVisitor : ASTNodeVisitor {
    private val declaredVariables = mutableSetOf<String>()
    private val usedVariables = mutableSetOf<String>()
    private val warnings = mutableListOf<String>()
    override fun visitProgramNode(node: ProgramNode): VisitorResult {
        val statements = node.statements
        statements.forEach { visit(it) }

        // Agarro las variables que fueron declaradas pero nunca usadas.
        val unusedVariables = declaredVariables - usedVariables

        // agrego un warning por cada variable de esas.
        unusedVariables.forEach {
            warnings.add("Variable '$it' is declared but never used.")
        }

        return if (warnings.isNotEmpty()) {
            VisitorResult.ListResult(warnings)
        } else {
            VisitorResult.Empty
        }
    }

    override fun visitAssignmentNode(node: AssignmentNode): VisitorResult {
        usedVariables.add(node.identifier.name)
        return VisitorResult.Empty
    }

    override fun visitPrintStatementNode(node: PrintStatementNode): VisitorResult {
        node.value.accept(this)
        return VisitorResult.Empty
    }

    override fun visitVariableDeclarationNode(node: VariableDeclarationNode): VisitorResult {
        declaredVariables.add(node.identifier.name)
        return VisitorResult.Empty
    }

    override fun visitLiteralNode(node: LiteralNode): VisitorResult {
        return VisitorResult.Empty
    }

    override fun visitBinaryExpressionNode(node: BinaryExpressionNode): VisitorResult {
        node.left.accept(this)
        node.right.accept(this)
        return VisitorResult.Empty
    }

    override fun visitIdentifierNode(node: IdentifierNode): VisitorResult {
        usedVariables.add(node.name)
        return VisitorResult.Empty
    }
}
