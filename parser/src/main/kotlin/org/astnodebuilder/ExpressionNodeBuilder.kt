package org.astnodebuilder

import org.Token
import org.astnode.ASTNode
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.ExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.LiteralValue
import org.expressionfactory.PatternFactory

class ExpressionNodeBuilder : ASTNodeBuilder {
    override val formula: String = "-"

    override fun generate(tokens: List<Token>): ASTNode {
        return parseExpression(tokens)
    }

    override fun checkFormula(tokensString: String): Boolean {
        // string with tokens separated by string enters. check that the string is a regex of identificationToken and PrintToken
        val expressionPattern = PatternFactory.getBinaryExpressionPattern()
        return Regex(expressionPattern).matches(tokensString)
    }

    private fun parseExpression(tokens: List<Token>): ExpressionNode {
        if (tokens.isEmpty()) throw IllegalArgumentException("Empty token list")

        return parseAdditiveExpression(tokens)
    }

    // Separo recursivamente por cada operador de suma o resta
    private fun parseAdditiveExpression(tokens: List<Token>): ExpressionNode {
        var left = parseMultiplicativeExpression(tokens.subList(0, findOperatorIndex(tokens, setOf("PlusToken", "MinusToken"))))

        for (i in tokens.indices) {
            if (tokens[i].type == "PlusToken" || tokens[i].type == "MinusToken") {
                val operatorToken = tokens[i]
                val rightTokens = tokens.subList(i + 1, tokens.size)
                val right = parseMultiplicativeExpression(rightTokens)

                left = BinaryExpressionNode(
                    type = "BinaryExpression",
                    location = operatorToken.location,
                    left = left,
                    right = right,
                    operator = operatorToken.value
                )
            }
        }

        return left
    }

    // Separo recursivamente por cada operador de multiplicación o división
    private fun parseMultiplicativeExpression(tokens: List<Token>): ExpressionNode {
        var left = parsePrimaryExpression(tokens.subList(0, findOperatorIndex(tokens, setOf("MultiplyToken", "DivisionToken"))))

        for (i in tokens.indices) {
            if (tokens[i].type == "MultiplyToken" || tokens[i].type == "DivisionToken") {
                val operatorToken = tokens[i]
                val rightTokens = tokens.subList(i + 1, tokens.size)
                val right = parsePrimaryExpression(rightTokens)

                left = BinaryExpressionNode(
                    type = "BinaryExpression",
                    location = operatorToken.location,
                    left = left,
                    right = right,
                    operator = operatorToken.value
                )
            }
        }

        return left
    }

    private fun parsePrimaryExpression(tokens: List<Token>): ExpressionNode {
        if (tokens.size == 1) {
            return when (tokens[0].type) {
                "NumberToken" -> LiteralNode(
                    type = "Literal",
                    location = tokens[0].location,
                    value = LiteralValue.NumberValue(checkIfInteger(tokens[0].value.toDouble()))
                )
                "StringToken" -> LiteralNode(
                    type = "Literal",
                    location = tokens[0].location,
                    value = LiteralValue.StringValue(tokens[0].value.trim('\'', '"'))
                )
                "IdentifierToken" -> IdentifierNodeBuilder().generate(tokens) as IdentifierNode
                else -> throw IllegalArgumentException("Unexpected token type: ${tokens[0].type}")
            }
        } else {
            return parseExpression(tokens)
        }
    }

    private fun checkIfInteger(num: Double): Number {
        return if (num % 1 == 0.0) num.toInt() else num
    }

    private fun findOperatorIndex(tokens: List<Token>, operators: Set<String>): Int {
        for (i in tokens.indices) {
            if (tokens[i].type in operators) {
                return i
            }
        }
        return tokens.size // If no operator found, return the size of the list
    }
}
