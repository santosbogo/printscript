package org.astnodebuilder

import org.shared.Token
import org.shared.astnode.ASTNode
import org.shared.astnode.expressionnode.ExpressionNode
import org.shared.astnode.expressionnode.IdentifierNode
import org.common.astnode.statementnode.AssignmentNode

class AssignmentNodeBuilder: ASTNodeBuilder {
    override val formula: String = "IdentifierToken AssignationToken ExpressionNode SemicolonToken"

    override fun generate(tokens: List<Token>): ASTNode {
        return AssignmentNode(
            type = "AssignmentNode",
            location = tokens[0].location,
            value = ExpressionNodeBuilder().generate(tokens.subList(2, tokens.size - 2)) as ExpressionNode,
            identifierNode = IdentifierNodeBuilder().generate(tokens.subList(0, 1)) as IdentifierNode
        )
    }

    override fun checkFormula(tokensString: String): Boolean {
        val expressionPattern = "(IdentifierToken|StringToken|NumberToken|PlusToken|MinusToken|MultiplyToken|DivisionToken)*"
        val pattern = "IdentifierToken AssignationToken $expressionPattern SemicolonToken"
        return Regex(pattern).matches(tokensString)
    }

}