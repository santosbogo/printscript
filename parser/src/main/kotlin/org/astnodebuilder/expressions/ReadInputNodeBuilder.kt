package org.astnodebuilder.expressions

import org.Parser
import org.Token
import org.astnode.ASTNode
import org.astnode.expressionnode.ExpressionNode
import org.astnode.expressionnode.ReadInputNode
import org.astnodebuilder.ASTNodeBuilder
import org.astnodebuilder.ExpressionNodeBuilder
import org.expressionfactory.PatternFactory

class ReadInputNodeBuilder : ASTNodeBuilder {
    override val formula = PatternFactory.getReadInputPattern()

    override fun generate(tokens: List<Token>, parser: Parser): ASTNode {
        if (tokens.isEmpty()) throw IllegalArgumentException("Empty token list")

        return ReadInputNode(
            type = "ReadInput",
            location = tokens[0].location,
            message = ExpressionNodeBuilder().generate(tokens.subList(2, tokens.size - 1), parser) as ExpressionNode,
        )
    }

    override fun checkFormula(tokensString: String): Boolean {
        return Regex(formula).matches(tokensString)
    }
}
