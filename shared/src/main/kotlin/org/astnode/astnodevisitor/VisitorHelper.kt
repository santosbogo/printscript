package org.astnode.astnodevisitor

import org.astnode.expressionnode.LiteralValue

class VisitorHelper {
    fun evaluateBinaryExpression(
        leftValue: LiteralValue,
        rightValue: LiteralValue,
        operator: String
    ): LiteralValue {
        return when (operator) {
            "+" -> handlePlus(leftValue, rightValue)
            "-" -> handleMinus(leftValue, rightValue)
            "*" -> handleMultiplication(leftValue, rightValue)
            "/" -> handleDivision(leftValue, rightValue)
            else -> throw UnsupportedOperationException("Unsupported operator: $operator")
        }
    }

    private fun handleNumberCase(left: Double, right: Double, operator: (Double, Double) -> Double): Number {
        return if (left % 1 == 0.0 && right % 1 == 0.0) operator(left, right).toInt()
        else operator(left, right)
    }

    private fun handlePlus(leftValue: LiteralValue, rightValue: LiteralValue): LiteralValue {
        return when {
            (leftValue is LiteralValue.StringValue) || (rightValue is LiteralValue.StringValue) ->
                LiteralValue.StringValue(leftValue.toString() + rightValue.toString())

            leftValue is LiteralValue.NumberValue && rightValue is LiteralValue.NumberValue ->
                LiteralValue.NumberValue(
                    handleNumberCase(leftValue.value.toDouble(), rightValue.value.toDouble()) { a, b -> a + b }
                )

            else -> throw UnsupportedOperationException("Unsupported types for +")
        }
    }

    private fun handleMinus(leftValue: LiteralValue, rightValue: LiteralValue): LiteralValue {
        return when {
            leftValue is LiteralValue.NumberValue && rightValue is LiteralValue.NumberValue ->
                LiteralValue.NumberValue(
                    handleNumberCase(leftValue.value.toDouble(), rightValue.value.toDouble()) { a, b -> a - b }
                )

            else -> throw UnsupportedOperationException("Unsupported types for -")
        }
    }

    private fun handleMultiplication(leftValue: LiteralValue, rightValue: LiteralValue): LiteralValue {
        return when {
            leftValue is LiteralValue.NumberValue && rightValue is LiteralValue.NumberValue ->
                LiteralValue.NumberValue(
                    handleNumberCase(leftValue.value.toDouble(), rightValue.value.toDouble()) { a, b -> a * b }
                )

            else -> throw UnsupportedOperationException("Unsupported types for *")
        }
    }

    private fun handleDivision(leftValue: LiteralValue, rightValue: LiteralValue): LiteralValue {
        return when {
            rightValue is LiteralValue.NumberValue && rightValue.value.toDouble() == 0.0 ->
                throw ArithmeticException("Division by zero")

            leftValue is LiteralValue.NumberValue && rightValue is LiteralValue.NumberValue ->
                LiteralValue.NumberValue(
                    handleNumberCase(leftValue.value.toDouble(), rightValue.value.toDouble()) { a, b -> a / b }
                )

            else -> throw UnsupportedOperationException("Unsupported types for /")
        }
    }
}
