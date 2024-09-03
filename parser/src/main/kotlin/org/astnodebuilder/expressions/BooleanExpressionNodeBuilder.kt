package org.astnodebuilder.expressions

import org.Token
import org.astnode.ASTNode
import org.astnode.expressionnode.LiteralNode
import org.astnode.expressionnode.LiteralValue
import org.astnodebuilder.ASTNodeBuilder
import org.astnodebuilder.IdentifierNodeBuilder

class BooleanExpressionNodeBuilder : ASTNodeBuilder {
    override val formula = "BooleanToken|IdentifierToken"

    override fun generate(tokens: List<Token>): ASTNode {
        if (tokens.size != 1) {
            throw IllegalArgumentException("Not supporting boolean expressions with more than one token")
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
            "IdentifierToken" -> IdentifierNodeBuilder().generate(tokens)
            else -> throw IllegalArgumentException("Unexpected token type ${tokens[0].type}")
        }
    }

    override fun checkFormula(tokensString: String): Boolean {
        return Regex(formula).matches(tokensString)
    }
}
