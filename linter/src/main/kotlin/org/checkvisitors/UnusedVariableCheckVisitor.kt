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

        return VisitorResult.ListResult(warnings)
    }

    override fun visitAssignmentNode(node: AssignmentNode): VisitorResult {
        usedVariables.add(node.identifierNode.name)
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
