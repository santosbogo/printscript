package org.astnodebuilder.expressions

import org.Parser
import org.Token
import org.astnode.ASTNode
import org.astnode.expressionnode.BinaryExpressionNode
import org.astnode.expressionnode.ExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.LiteralValue

import org.astnodebuilder.ASTNodeBuilder
import org.astnodebuilder.IdentifierNodeBuilder
import org.expressionfactory.PatternFactory
import java.util.LinkedList

class BinaryExpressionNodeBuilder : ASTNodeBuilder {
    override val formula = PatternFactory.getBinaryExpressionPattern()

    override fun generate(tokens: List<Token>, parser: Parser): ASTNode {
        if (tokens.isEmpty()) throw IllegalArgumentException("Empty token list")
        val postfixNotation = getPostfixNotation(tokens)
        val expression = evaluatePostfixNotation(postfixNotation, parser)
        return expression
    }

    override fun checkFormula(tokensString: String): Boolean {
        return Regex(formula).matches(tokensString)
    }

    private fun getPostfixNotation(tokens: List<Token>): List<Token> {
        val postfixNotation = mutableListOf<Token>()
        val stack = LinkedList<Token>()
        val operators = setOf("PlusToken", "MinusToken", "MultiplyToken", "DivisionToken")
        for (token in tokens) {
            if (token.type == "OpenParenthesisToken") {
                stack.addLast(token)
            } else if (token.type == "CloseParenthesisToken") {
                while (stack.isNotEmpty() && stack.last().type != "OpenParenthesisToken") {
                    postfixNotation.add(stack.removeLast())
                }
                stack.removeLast()
            } else if (operators.contains(token.type)) {
                while (stack.isNotEmpty() && operators.contains(stack.peekLast().type)) {
                    postfixNotation.add(stack.removeLast())
                }
                stack.addLast(token)
            } else {
                postfixNotation.add(token)
            }
        }
        while (stack.isNotEmpty()) {
            postfixNotation.add(stack.removeLast())
        }
        return postfixNotation
    }

    private fun evaluatePostfixNotation(postfixNotation: List<Token>, parser: Parser): ASTNode {
        val stack = LinkedList<ASTNode>()
        val operators = setOf("PlusToken", "MinusToken", "MultiplyToken", "DivisionToken")
        for (token in postfixNotation) {
            if (operators.contains(token.type)) {
                val right = stack.removeLast()
                val left = stack.removeLast()
                stack.addLast(
                    BinaryExpressionNode(
                        type = "BinaryExpression",
                        location = token.location,
                        left = left as ExpressionNode,
                        right = right as ExpressionNode,
                        operator = token.value
                    )
                )
            } else {
                stack.addLast(
                    when (token.type) {
                        "IdentifierToken" -> IdentifierNodeBuilder()
                            .generate(listOf(token), parser) as IdentifierNode
                        "StringToken" -> LiteralNode(
                            type = "Literal",
                            location = token.location,
                            value = LiteralValue.StringValue(token.value)
                        )
                        "NumberToken" -> LiteralNode(
                            type = "Literal",
                            location = token.location,
                            value = LiteralValue.NumberValue(checkIfInteger(token.value.toDouble()))
                        )
                        else -> throw IllegalArgumentException("Invalid token type")
                    }
                )
            }
        }
        return stack.removeLast()
    }

    private fun checkIfInteger(num: Double): Number {
        return if (num % 1 == 0.0) num.toInt() else num
    }
}
