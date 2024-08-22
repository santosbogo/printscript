package org.astnode.astnodevisitor

import org.astnode.ASTNode
import org.astnode.ProgramNode
import org.astnode.astnodevisitor.types.VisitorResult
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.statementnode.AssignmentNode
import org.astnode.statementnode.PrintStatementNode
import org.astnode.statementnode.VariableDeclarationNode

class InterpreterVisitor : ASTNodeVisitor {
    val symbolTable: MutableMap<String, LiteralValue> = mutableMapOf()

    override fun visit(node: ASTNode): VisitorResult {
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
        return VisitorResult.MapResult(symbolTable)
    }

    override fun visitAssignmentNode(node: AssignmentNode): VisitorResult {
        val variableIdentifier = node.identifierNode
        val value = node.value.accept(this) as VisitorResult.LiteralValueResult
        symbolTable[variableIdentifier.name] = value.value
        return VisitorResult.MapResult(symbolTable)
    }

    override fun visitPrintStatementNode(node: PrintStatementNode): VisitorResult {
        val value = node.value.accept(this) as VisitorResult.LiteralValueResult
        when (value.value) {
            is LiteralValue.StringValue -> {
                val stringValue = (value.value as LiteralValue.StringValue).value
                // Reemplaza todas las comillas simples y dobles, tanto internas como externas
                val cleanedStringValue = stringValue.replace("'", "").replace("\"", "")
                println(cleanedStringValue)
            }// printeo el valor, del literalValue que está en el literalValueResult.
            is LiteralValue.NumberValue -> println(value.value.value)
            else -> {
                throw UnsupportedOperationException("Unsupported type: ${value.value::class}")
            }
        }
        return VisitorResult.Empty
    }

    override fun visitVariableDeclarationNode(node: VariableDeclarationNode): VisitorResult {
        val variableIdentifier = node.identifier
        val value = node.init.accept(this) as VisitorResult.LiteralValueResult
        symbolTable[variableIdentifier.name] = value.value
        return VisitorResult.MapResult(symbolTable)
    }

    override fun visitLiteralNode(node: LiteralNode): VisitorResult {
        return VisitorResult.LiteralValueResult(node.value)
    }

    override fun visitIdentifierNode(node: IdentifierNode): VisitorResult {
        val value = symbolTable[node.name]
        if (value != null) {
            return when (value) {
                is LiteralValue.StringValue -> VisitorResult.LiteralValueResult(value)
                is LiteralValue.NumberValue -> VisitorResult.LiteralValueResult(value)
            }
        } else {
            throw Exception("Variable ${node.name} not declared")
        }
    }

    override fun visitBinaryExpressionNode(node: BinaryExpressionNode): VisitorResult {
        val leftResult = node.left.accept(this) as VisitorResult.LiteralValueResult
        val rightResult = node.right.accept(this) as VisitorResult.LiteralValueResult

        val leftValue = leftResult.value
        val rightValue = rightResult.value

        val resultLiteralValue: LiteralValue = when (node.operator) {
            "+" -> {
                when {
                    (leftValue is LiteralValue.StringValue) || (rightValue is LiteralValue.StringValue) ->
                        LiteralValue.StringValue(leftValue.toString() + rightValue.toString())

                    leftValue is LiteralValue.NumberValue && rightValue is LiteralValue.NumberValue ->
                        LiteralValue.NumberValue(handleNumberCase(leftValue.value.toDouble(), rightValue.value.toDouble()) { a, b -> a + b })

                    else -> throw UnsupportedOperationException("Unsupported types for +")
                }
            }

            "-" -> {
                when {
                    leftValue is LiteralValue.NumberValue && rightValue is LiteralValue.NumberValue ->
                        LiteralValue.NumberValue(handleNumberCase(leftValue.value.toDouble(), rightValue.value.toDouble()) { a, b -> a - b })

                    else -> throw UnsupportedOperationException("Unsupported types for -")
                }
            }

            "*" -> {
                when {
                    leftValue is LiteralValue.NumberValue && rightValue is LiteralValue.NumberValue ->
                        LiteralValue.NumberValue(handleNumberCase(leftValue.value.toDouble(), rightValue.value.toDouble()) { a, b -> a * b })

                    else -> throw UnsupportedOperationException("Unsupported types for *")
                }
            }

            "/" -> {
                when {
                    rightValue is LiteralValue.NumberValue && rightValue.value.toDouble() == 0.0 ->
                        throw ArithmeticException("Division by zero")

                    leftValue is LiteralValue.NumberValue && rightValue is LiteralValue.NumberValue ->
                        LiteralValue.NumberValue(handleNumberCase(leftValue.value.toDouble(), rightValue.value.toDouble()) { a, b -> a / b })

                    else -> throw UnsupportedOperationException("Unsupported types for /")
                }
            }

            else -> {
                throw UnsupportedOperationException("Unsupported operator: ${node.operator}")
            }
        }

        return VisitorResult.LiteralValueResult(resultLiteralValue)
    }

    private fun handleNumberCase(left: Double, right: Double, operator: (Double, Double) -> Double): Number {
        return if (left % 1 == 0.0 && right % 1 == 0.0) operator(left, right).toInt()
        else operator(left, right)
    }
}
