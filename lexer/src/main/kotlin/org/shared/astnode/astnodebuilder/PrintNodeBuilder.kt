package org.shared.astnode.astnodebuilder

import org.shared.Token
import org.shared.astnode.ASTNode
import org.shared.astnode.expressionnode.ExpressionNode
import org.shared.astnode.statementnode.PrintStatementNode

class PrintNodeBuilder: ASTNodeBuilder {
    override val formula: String = "PrintToken OpenParenthesisToken ExpressionNode CloseParenthesisToken  SemicolonToken"

    override fun generate(tokens: List<Token>): ASTNode {
        return PrintStatementNode(
            type = "PrintStatementNode",
            location = tokens[0].location,
            value = ExpressionNodeBuilder().generate(tokens.subList(2, tokens.size - 3)) as ExpressionNode
        )
    }
}