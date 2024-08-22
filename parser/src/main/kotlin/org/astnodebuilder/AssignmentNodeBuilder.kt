package org.astnodebuilder

import org.Token
import org.astnode.ASTNode
import org.astnode.expressionnode.ExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.statementnode.AssignmentNode
import org.expressionfactory.PatternFactory

class AssignmentNodeBuilder : ASTNodeBuilder {
    override val formula: String = "IdentifierToken AssignationToken ExpressionNode SemicolonToken"

    override fun generate(tokens: List<Token>): ASTNode {
        return AssignmentNode(
            type = "AssignmentNode",
            location = tokens[0].location,
            value = ExpressionNodeBuilder().generate(tokens.subList(2, tokens.size - 1)) as ExpressionNode,
            identifierNode = IdentifierNodeBuilder().generate(tokens.subList(0, 1)) as IdentifierNode
        )
    }

    override fun checkFormula(tokensString: String): Boolean {
        val expressionPattern = PatternFactory.getBinaryExpressionPattern()
        val pattern = "^IdentifierToken\\s+AssignationToken\\s+${expressionPattern.drop(1).dropLast(1)}" +
            "\\s+SemicolonToken$"
        return Regex(pattern).matches(tokensString)
    }
}
