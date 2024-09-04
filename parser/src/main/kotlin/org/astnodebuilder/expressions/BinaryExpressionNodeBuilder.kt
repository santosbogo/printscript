package org.astnodebuilder.expressions

import org.Parser
import org.Token
import org.astnode.ASTNode
import org.astnode.expressionnode.ExpressionNode
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.LiteralValue
import org.astnodebuilder.ASTNodeBuilder
import org.astnodebuilder.IdentifierNodeBuilder
import org.expressionfactory.PatternFactory

class BinaryExpressionNodeBuilder : ASTNodeBuilder {
    override val formula = PatternFactory.getBinaryExpressionPattern()

    override fun generate(tokens: List<Token>, parser: Parser): ASTNode {
        if (tokens.isEmpty()) throw IllegalArgumentException("Empty token list")

        return parseAdditiveExpression(tokens, parser)
    }

    override fun checkFormula(tokensString: String): Boolean {
        return Regex(formula).matches(tokensString)
    }

    // Separo recursivamente por cada operador de suma o resta
    private fun parseAdditiveExpression(tokens: List<Token>, parser: Parser): ExpressionNode {
        var left = parseMultiplicativeExpression(
            tokens.subList(
                0,
                findOperatorIndex(tokens, setOf("PlusToken", "MinusToken"))
            ),
            parser
        )

        for (i in tokens.indices) {
            if (tokens[i].type == "PlusToken" || tokens[i].type == "MinusToken") {
                val operatorToken = tokens[i]
                val rightTokens = tokens.subList(i + 1, tokens.size)
                val right = parseMultiplicativeExpression(rightTokens, parser)

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
    private fun parseMultiplicativeExpression(tokens: List<Token>, parser: Parser): ExpressionNode {
        var left = parsePrimaryExpression(
            tokens.subList(
                0,
                findOperatorIndex(tokens, setOf("MultiplyToken", "DivisionToken"))
            ),
            parser
        )

        for (i in tokens.indices) {
            if (tokens[i].type == "MultiplyToken" || tokens[i].type == "DivisionToken") {
                val operatorToken = tokens[i]
                val rightTokens = tokens.subList(i + 1, tokens.size)
                val right = parsePrimaryExpression(rightTokens, parser)

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

    private fun parsePrimaryExpression(tokens: List<Token>, parser: Parser): ExpressionNode {
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
                    value = LiteralValue.StringValue(tokens[0].value)
                )
                "IdentifierToken" -> IdentifierNodeBuilder().generate(tokens, parser) as IdentifierNode
                else -> throw IllegalArgumentException("Unexpected token type: ${tokens[0].type}")
            }
        } else {
            return generate(tokens, parser) as ExpressionNode
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
