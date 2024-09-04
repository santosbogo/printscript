package org.astnodebuilder

import org.Parser
import org.Token
import org.astnode.ASTNode
import org.astnode.expressionnode.ExpressionNode
import org.astnode.statementnode.PrintStatementNode
import org.expressionfactory.PatternFactory

class PrintNodeBuilder : ASTNodeBuilder {
    override val formula: String = "PrintToken OpenParenthesisToken ExpressionNode CloseParenthesisToken SemicolonToken"

    override fun generate(tokens: List<Token>, parser: Parser): ASTNode {
        return PrintStatementNode(
            type = "PrintStatementNode",
            location = tokens[0].location,
            value = ExpressionNodeBuilder().generate(tokens.subList(2, tokens.size - 2), parser) as ExpressionNode
        )
    }

    override fun checkFormula(tokensString: String): Boolean {
        val expressionPattern = PatternFactory.getExpressionPattern()
        val pattern = "^PrintToken\\s*OpenParenthesisToken\\s*$expressionPattern\\s*CloseParenthesisToken\\s*SemicolonToken$"
        return Regex(pattern).matches(tokensString)
    }
}
