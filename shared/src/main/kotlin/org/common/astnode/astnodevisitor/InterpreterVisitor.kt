package org.common.astnode.astnodevisitor

import org.shared.astnode.ASTNode
import org.common.astnode.ProgramNode
import org.common.astnode.astnodevisitor.types.VisitorResult
import org.common.astnode.statementnode.AssignmentNode
import org.common.astnode.statementnode.PrintStatementNode
import org.common.astnode.statementnode.VariableDeclarationNode
import org.shared.astnode.expressionnode.BinaryExpressionNode
import org.shared.astnode.expressionnode.IdentifierNode
import org.shared.astnode.expressionnode.LiteralNode
import org.shared.astnode.expressionnode.LiteralValue

class InterpreterVisitor : ASTNodeVisitor {
    override val symbolTable: MutableMap<String, Any> = mutableMapOf()

    override fun visit(node: ASTNode): Any {
        return when (node) {
            is ProgramNode -> visitProgramNode(node)
            is AssignmentNode -> visitAssignmentNode(node)
            is PrintStatementNode -> visitPrintStatementNode(node)
            is VariableDeclarationNode -> visitVariableDeclarationNode(node)
            is LiteralNode -> visitLiteralNode(node)
            is IdentifierNode -> visitIdentifierNode(node)
            is BinaryExpressionNode -> visitBinaryExpressionNode(node)
            else -> throw UnsupportedOperationException("Unsupported node: ${node::class}")
        }
    }


    override fun visitProgramNode(node: ProgramNode): VisitorResult {
        val statements = node.statements
        statements.forEach { it.accept(this) }
        return VisitorResult(null, symbolTable)
    }

    override fun visitAssignmentNode(node: AssignmentNode): VisitorResult {
        val variableIdentifier = node.identifierNode
        val value = node.value.accept(this)
        symbolTable[variableIdentifier.name] = value
        return VisitorResult(null, symbolTable)
    }

    override fun visitPrintStatementNode(node: PrintStatementNode): VisitorResult {
        when (val value = node.value.accept(this).literalValue) {
            is LiteralValue.StringValue -> println(value.value)
            is LiteralValue.NumberValue -> println(value.value)
            null -> throw Exception("Value is null")
        }
        return VisitorResult(null, emptyMap()) //TODO no me cierra esto, probar cambiar la interfaz de este metodo.
    }

    override fun visitVariableDeclarationNode(node: VariableDeclarationNode): VisitorResult {
        val variableIdentifier = node.identifier
        val value = node.init.accept(this)
        symbolTable[variableIdentifier.name] = value
        return VisitorResult(null, symbolTable)
    }

    override fun visitLiteralNode(node: LiteralNode): VisitorResult {
        // Devuelvo el valor tal cual, para que pueda ser usado en su contexto(asignacion o expresion)
        return when (val literalValue = node.value) {
            is LiteralValue.StringValue -> VisitorResult( LiteralValue.StringValue(literalValue.value), symbolTable)
            is LiteralValue.NumberValue -> VisitorResult( LiteralValue.NumberValue(literalValue.value), symbolTable)
        }

    }

    override fun visitIdentifierNode(node: IdentifierNode): VisitorResult {
        val value = symbolTable[node.name]
        if (value != null) {
            return when (value) {
                //devuelvo el valor q tiene asignado, para que pueda ser usado en su contexto(asignacion/printeo/expresion)
                is String -> VisitorResult( LiteralValue.StringValue(value), symbolTable)
                is Number -> VisitorResult( LiteralValue.NumberValue(value), symbolTable)
                else -> throw UnsupportedOperationException("Unsupported type: ${value::class}")
            }
        } else {
            throw Exception("Variable ${node.name} not declared")
        }
    }

    override fun visitBinaryExpressionNode(node: BinaryExpressionNode): VisitorResult {
        val leftResult = node.left.accept(this)
        val rightResult = node.right.accept(this)

        val leftValue = leftResult.literalValue
        val rightValue = rightResult.literalValue

        val resultLiteralValue: LiteralValue = when (node.operator) {
            "+" -> {
                when {
                    (leftValue is LiteralValue.StringValue) || (rightValue is LiteralValue.StringValue) ->
                        LiteralValue.StringValue(leftValue.toString() + rightValue.toString())

                    leftValue is LiteralValue.NumberValue && rightValue is LiteralValue.NumberValue ->
                        LiteralValue.NumberValue(leftValue.value.toDouble() + rightValue.value.toDouble())

                    else -> throw UnsupportedOperationException("Unsupported types for +")
                }
            }

            "-" -> {
                when {
                    leftValue is LiteralValue.NumberValue && rightValue is LiteralValue.NumberValue ->
                        LiteralValue.NumberValue(leftValue.value.toDouble() - rightValue.value.toDouble())

                    else -> throw UnsupportedOperationException("Unsupported types for -")
                }
            }

            "*" -> {
                when {
                    leftValue is LiteralValue.NumberValue && rightValue is LiteralValue.NumberValue ->
                        LiteralValue.NumberValue(leftValue.value.toDouble() * rightValue.value.toDouble())

                    else -> throw UnsupportedOperationException("Unsupported types for -")
                }
            }

            "/" -> {
                when {
                    rightValue is LiteralValue.NumberValue && rightValue.value.toDouble() == 0.0 ->
                        throw ArithmeticException("Division by zero")

                    leftValue is LiteralValue.NumberValue && rightValue is LiteralValue.NumberValue ->
                        LiteralValue.NumberValue(leftValue.value.toDouble() / rightValue.value.toDouble())

                    else -> throw UnsupportedOperationException("Unsupported types for /")
                }
            }

            else -> {
                throw UnsupportedOperationException("Unsupported operator: ${node.operator}")
            }
        }

        return VisitorResult(
            literalValue = resultLiteralValue,
            map = symbolTable
        )
    }
}
