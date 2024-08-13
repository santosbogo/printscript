package org.common.astnode.astnodevisitor

import org.shared.astnode.ASTNode
import org.common.astnode.ProgramNode
import org.shared.astnode.astnodevisitor.ASTNodeVisitor
import org.shared.astnode.statementnode.AssignmentNode
import org.shared.astnode.statementnode.PrintStatementNode
import org.shared.astnode.statementnode.VariableDeclarationNode
import org.shared.astnode.expressionnode.BinaryExpressionNode
import org.shared.astnode.expressionnode.IdentifierNode
import org.shared.astnode.expressionnode.LiteralNode
import org.shared.astnode.expressionnode.LiteralValue

class InterpreterVisitor(private val symbolTable: MutableMap<String, Any>) : ASTNodeVisitor {

    fun visit(node: ASTNode): LiteralValue? {
        return when (node) {
            is ProgramNode -> visitProgramNode(node)
            is AssignmentNode -> visitAssignmentNode(node)
            is PrintStatementNode -> visitPrintStatementNode(node)
            is VariableDeclarationNode -> visitVariableDeclarationNode(node)
            is LiteralNode -> visitLiteralNode(node)
            is IdentifierNode -> visitIdentifierNode(node)
            is BinaryExpressionNode -> visitBinaryExpressionNode(node)
            else -> throw UnsupportedOperationException("Node type not supported")
        }
    }

    override fun visitProgramNode(node: ProgramNode): LiteralValue? {
        val statements = node.statements
        statements.forEach { it.accept(this) }
        return null
    }

    override fun visitAssignmentNode(node: AssignmentNode): LiteralValue? {
        val variableIdentifier = node.identifierNode
        val value = node.value.accept(this)
        symbolTable[variableIdentifier.name] = value
        return null
    }

    override fun visitPrintStatementNode(node: PrintStatementNode): LiteralValue? {
        when (val value = node.value.accept(this)) {
            is LiteralValue.StringValue -> println(value.value)
            is LiteralValue.NumberValue -> println(value.value)
        }
        return null
    }

    override fun visitVariableDeclarationNode(node: VariableDeclarationNode): LiteralValue? {
        val variableIdentifier = node.identifier
        val value = node.init.accept(this)
        symbolTable[variableIdentifier.name] = value
        return null
    }

    override fun visitLiteralNode(node: LiteralNode): LiteralValue {
        // Devuelvo el valor tal cual, para que pueda ser usado en su contexto(asignacion o expresion)
        return when (val literalValue = node.value) {
            is LiteralValue.StringValue -> LiteralValue.StringValue(literalValue.value)
            is LiteralValue.NumberValue -> LiteralValue.NumberValue(literalValue.value)
        }

    }

    override fun visitIdentifierNode(node: IdentifierNode): LiteralValue {
        val value = symbolTable[node.name]
        if (value != null) {
            return when (value) {
                //devuelvo el valor q tiene asignado, para que pueda ser usado en su contexto(asignacion/printeo/expresion)
                is String -> LiteralValue.StringValue(value)
                is Number -> LiteralValue.NumberValue(value)
                else -> throw UnsupportedOperationException("Unsupported type: ${value::class}")
            }
        } else {
            throw Exception("Variable ${node.name} not declared")
        }
    }

    override fun visitBinaryExpressionNode(node: BinaryExpressionNode): LiteralValue {
        val leftValue = node.left.accept(this)
        val rightValue = node.right.accept(this)

        return when (node.operator) {
            "+" -> {
                when {
                    leftValue is LiteralValue.StringValue || rightValue is LiteralValue.StringValue ->
                        LiteralValue.StringValue(leftValue.toString() + rightValue.toString())

                    leftValue is LiteralValue.NumberValue && rightValue is LiteralValue.NumberValue ->
                        LiteralValue.NumberValue(leftValue.value.toDouble() + rightValue.value.toDouble())

                    else -> throw UnsupportedOperationException("Unsupported types for +")
                }
            }

            else -> {
                throw UnsupportedOperationException("Unsupported operator: ${node.operator}")
            }
        }
    }
}
