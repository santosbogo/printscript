package org.astnodebuilder

import org.shared.Token
import org.shared.astnode.ASTNode
import org.shared.astnode.expressionnode.ExpressionNode
import org.common.astnode.statementnode.PrintStatementNode

class PrintNodeBuilder: ASTNodeBuilder {
    override val formula: String = "PrintToken OpenParenthesisToken ExpressionNode CloseParenthesisToken SemicolonToken"

    override fun generate(tokens: List<Token>): ASTNode {
        return PrintStatementNode(
            type = "PrintStatementNode",
            location = tokens[0].location,
            value = ExpressionNodeBuilder().generate(tokens.subList(2, tokens.size - 3)) as ExpressionNode
        )
    }

    override fun checkFormula(tokensString: String): Boolean {
        val expressionPattern = "(IdentifierToken|StringToken|NumberToken|PlusToken|MinusToken|MultiplyToken|DivisionToken)*"
        val pattern = "PrintToken OpenParenthesisToken $expressionPattern CloseParenthesisToken SemicolonToken"
        return Regex(pattern).matches(tokensString)
    }
}