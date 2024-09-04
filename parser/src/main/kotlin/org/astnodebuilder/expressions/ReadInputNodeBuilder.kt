package org.astnodebuilder.expressions

import org.Parser
import org.Token
import org.astnode.ASTNode
import org.astnode.expressionnode.ExpressionNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.expressionnode.ReadInputNode
import org.astnodebuilder.ASTNodeBuilder
import org.expressionfactory.PatternFactory

class ReadInputNodeBuilder : ASTNodeBuilder {
    override val formula = PatternFactory.getReadInputPattern()

    override fun generate(tokens: List<Token>, parser: Parser): ASTNode {
        if (tokens.isEmpty()) throw IllegalArgumentException("Empty token list")

        val messageNode: ExpressionNode = convertTokenToExpressionNode(tokens[2])

        return ReadInputNode(
            type = "ReadInput",
            location = tokens[0].location,
            message = messageNode
        )
    }

    override fun checkFormula(tokensString: String): Boolean {
        return Regex(formula).matches(tokensString)
    }

    private fun convertTokenToExpressionNode(token: Token): ExpressionNode {
        return when (token.type) {
            "StringToken" -> LiteralNode(
                type = "LiteralNode",
                location = token.location,
                value = LiteralValue.StringValue(token.value)
            )
            else -> throw IllegalArgumentException("Unexpected token type ${token.type}")
        }
    }
}
