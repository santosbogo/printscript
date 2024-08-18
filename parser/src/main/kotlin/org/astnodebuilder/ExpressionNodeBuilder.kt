package org.astnodebuilder

import org.common.expressionfactory.PatternFactory
import org.common.Token
import org.common.astnode.ASTNode
import org.common.astnode.expressionnode.BinaryExpressionNode
import org.common.astnode.expressionnode.ExpressionNode
import org.common.astnode.expressionnode.LiteralNode
import org.common.astnode.expressionnode.LiteralValue

class ExpressionNodeBuilder : ASTNodeBuilder {
    override val formula: String = "-"

    override fun generate(tokens: List<Token>): ASTNode {
        return parseExpression(tokens)
    }

    override fun checkFormula(tokensString: String): Boolean {
        //string with tokens separated by string enters. check that the string is a regex of identificationToken and PrintToken
        val expressionPattern = PatternFactory.getBinaryExpressionPattern()
        return Regex(expressionPattern).matches(tokensString)
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
                "IdentifierToken" -> IdentifierNodeBuilder.generateNodeFromValue(tokens[0].value, tokens[0].location)

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
