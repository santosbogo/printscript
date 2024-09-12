package org.astnodebuilder.expressions

import org.Parser
import org.Token
import org.astnode.ASTNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.LiteralValue
import org.astnodebuilder.ASTNodeBuilder
import org.astnodebuilder.IdentifierNodeBuilder
import org.expressionfactory.PatternFactory

class BooleanExpressionNodeBuilder : ASTNodeBuilder {
    override val formula = PatternFactory.getBooleanExpressionPattern()

    override fun generate(tokens: List<Token>, parser: Parser): ASTNode {
        if (tokens.size != 1) {
            throw IllegalArgumentException(
                "Not supporting boolean expressions with more than one token"
            )
        }

        return when (tokens[0].type) {
            "BooleanToken" -> {
                val value = tokens[0].value.toBoolean()
                LiteralNode(
                    type = "LiteralNode",
                    location = tokens[0].location,
                    value = LiteralValue.BooleanValue(value)
                )
            }
            "IdentifierToken" -> IdentifierNodeBuilder().generate(tokens, parser)
            else -> throw IllegalArgumentException("Unexpected token type ${tokens[0].type}")
        }
    }

    override fun checkFormula(tokensString: String): Boolean {
        return Regex(formula).matches(tokensString)
    }
}
