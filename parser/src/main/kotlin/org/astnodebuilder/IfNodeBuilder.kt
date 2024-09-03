package org.astnodebuilder

import org.Parser
import org.Token
import org.Utils
import org.astnode.ASTNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.statementnode.IfNode
import org.expressionfactory.PatternFactory

class IfNodeBuilder : ASTNodeBuilder {
    override val formula: String = "IfToken OpenParenthesisToken BooleanToken CloseParenthesisToken OpenBraceToken ExpressionNode CloseBraceToken " +
        "ElseToken OpenBraceToken ExpressionNode CloseBraceToken"

    override fun generate(tokens: List<Token>): ASTNode {
        val ifTokens = Utils().getIfTokens(tokens, 0)
        if (checkIfElseStructure(Utils().getFormula(tokens)) == "if") {
            return IfNode(
                type = "IfNode",
                location = tokens[0].location,
                boolean = LiteralValue.BooleanValue(tokens[2].value.toBoolean()),
                ifExpression = Parser().parse(ifTokens.subList(4, ifTokens.size - 1)).programNode!!.statements,
                elseExpression = emptyList(),
            )
        }
        return IfNode( // TODO(falta obtener los token para ifExpression & elseExpression)
            type = "IfNode",
            location = tokens[0].location,
            boolean = LiteralValue.BooleanValue(tokens[2].value.toBoolean()),
            ifExpression = Parser().parse(ifTokens.subList(5, ifTokens.size - 1)).programNode!!.statements,
            elseExpression = emptyList()
        )
    }

    override fun checkFormula(tokensString: String): Boolean {
        val pattern = "^IfToken\\s+OpenParenthesisToken\\s+BooleanToken\\s+CloseParenthesisToken\\s+OpenBraceToken\\s+.*?\\s+CloseBraceToken$"
        return Regex(pattern).matches(tokensString)
    }

    // Check if the tokens follow an if-else or if structure
    private fun checkIfElseStructure(tokensString: String): String {
        val ifPattern = PatternFactory.getIfPattern()
        val ifElsePattern = PatternFactory.getIfWithElsePattern()
        return when {
            Regex(ifElsePattern).matches(tokensString) -> "if-else"
            Regex(ifPattern).matches(tokensString) -> "if"
            else -> "none"
        }
    }
}
