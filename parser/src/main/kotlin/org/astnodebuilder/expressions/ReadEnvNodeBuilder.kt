package org.astnodebuilder.expressions

import org.Parser
import org.Token
import org.astnode.ASTNode
import org.astnode.expressionnode.ReadEnvNode
import org.astnodebuilder.ASTNodeBuilder
import org.expressionfactory.PatternFactory

class ReadEnvNodeBuilder : ASTNodeBuilder {
    override val formula = PatternFactory.getReadEnvironmentPattern()

    override fun generate(tokens: List<Token>, parser: Parser): ASTNode {
        if (tokens.isEmpty()) throw IllegalArgumentException("Empty token list")

        return ReadEnvNode(
            type = "ReadEnv",
            location = tokens[0].location,
            variableName = tokens[2].value
        )
    }

    override fun checkFormula(tokensString: String): Boolean {
        return Regex(formula).matches(tokensString)
    }
}
