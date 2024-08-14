package org.astnodebuilder

import org.astnodebuilder.expressionfactory.PatternFactory
import org.common.Token
import org.common.astnode.ASTNode
import org.common.astnode.expressionnode.ExpressionNode
import org.common.astnode.expressionnode.IdentifierNode
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
        val expressionPattern = PatternFactory.getExpressionPattern()
        val pattern = "^IdentifierToken\\s+AssignationToken\\s+${expressionPattern.drop(1).dropLast(1)}\\s+SemicolonToken$"
        return Regex(pattern).matches(tokensString)
    }
}