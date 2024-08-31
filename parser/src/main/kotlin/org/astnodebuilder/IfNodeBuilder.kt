package org.astnodebuilder

import org.Token
import org.astnode.ASTNode
import org.astnode.expressionnode.ExpressionNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.statementnode.IfNode

class IfNodeBuilder : ASTNodeBuilder {
    override val formula: String = "IfToken OpenParenthesisToken BooleanToken CloseParenthesisToken OpenBraceToken ExpressionNode CloseBraceToken " +
        "ElseToken OpenBraceToken ExpressionNode CloseBraceToken"

    override fun generate(tokens: List<Token>): ASTNode {
        if (tokens.size == 7) {
            return IfNode(
                type = "IfNode",
                location = tokens[0].location,
                boolean = LiteralValue.BooleanValue(tokens[2].value.toBoolean()),
                ifExpression = ExpressionNodeBuilder().generate(tokens.subList(5, 6)) as ExpressionNode,
                elseExpression = null,
            )
        }
        return IfNode(
            type = "IfNode",
            location = tokens[0].location,
            boolean = LiteralValue.BooleanValue(tokens[2].value.toBoolean()),
            ifExpression = ExpressionNodeBuilder().generate(tokens.subList(5, 6)) as ExpressionNode,
            elseExpression = ExpressionNodeBuilder().generate(tokens.subList(9, 10)) as ExpressionNode
        )
    }

    override fun checkFormula(tokensString: String): Boolean {
        return formula == tokensString
    }
}
