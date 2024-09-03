package org.astnodebuilder

import org.Token
import org.astnode.ASTNode
import org.astnodebuilder.expressions.ExpressionsNodeBuilderFactory

class ExpressionNodeBuilder(
    private val expressionsBuilders: List<ASTNodeBuilder> = ExpressionsNodeBuilderFactory().createV11()
) : ASTNodeBuilder {
    override val formula: String = ""

    override fun generate(tokens: List<Token>): ASTNode {
        for (builder in expressionsBuilders) {
            if (builder.checkFormula(tokens.joinToString(" ") { it.type })) {
                return builder.generate(tokens)
            }
        }
        throw IllegalArgumentException("Invalid expression generation at ${tokens[0].location}")
    }

    override fun checkFormula(tokensString: String): Boolean {
        for (builder in expressionsBuilders) {
            if (builder.checkFormula(tokensString)) {
                return true
            }
        }
        return false
    }
}
