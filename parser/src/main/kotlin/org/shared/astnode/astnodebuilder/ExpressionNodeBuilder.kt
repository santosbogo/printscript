package org.shared.astnode.astnodebuilder

import org.shared.Token
import org.shared.astnode.ASTNode
import org.shared.astnode.expressionnode.BinaryExpressionNode
import org.shared.astnode.expressionnode.ExpressionNode
import org.shared.astnode.expressionnode.LiteralNode
import org.shared.astnode.expressionnode.LiteralValue

class ExpressionNodeBuilder : ASTNodeBuilder {
    override val formula: String = "Expression"

    override fun generate(tokens: List<Token>): ASTNode {
        return parseExpression(tokens)
    }

    private fun parseExpression(tokens: List<Token>): ExpressionNode {
        if (tokens.isEmpty()) throw IllegalArgumentException("Empty token list")

        // Handle single token expressions
        if (tokens.size == 1) {
            return when (tokens[0].type) {
                "NumberToken" -> LiteralNode(
                    type = "Literal",
                    location = tokens[0].location,
                    value = LiteralValue.NumberValue(tokens[0].value.toDouble())
                )
                "StringToken" -> LiteralNode(
                    type = "Literal",
                    location = tokens[0].location,
                    value = LiteralValue.StringValue(tokens[0].value)
                )
                else -> throw IllegalArgumentException("Unexpected token type: ${tokens[0].type}")
            }
        }

        // Handle binary expressions
        val operatorIndex = findOperatorIndex(tokens)
        val leftTokens = tokens.subList(0, operatorIndex)
        val rightTokens = tokens.subList(operatorIndex + 1, tokens.size)
        val operatorToken = tokens[operatorIndex]

        return BinaryExpressionNode(
            type = "BinaryExpression",
            location = operatorToken.location,
            left = parseExpression(leftTokens),
            right = parseExpression(rightTokens),
            operator = operatorToken.value
        )
    }

    private fun findOperatorIndex(tokens: List<Token>): Int {
        val operators = setOf("PlusToken", "MinusToken", "MultiplyToken", "DivisionToken")
        for (i in tokens.indices) {
            if (tokens[i].type in operators) {
                return i
            }
        }
        throw IllegalArgumentException("No operator found in token list")
    }
}
