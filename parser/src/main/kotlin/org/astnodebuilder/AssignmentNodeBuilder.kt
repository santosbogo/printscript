package org.astnodebuilder

import org.Parser
import org.Token
import org.astnode.ASTNode
import org.astnode.expressionnode.ExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.statementnode.AssignmentNode
import org.expressionfactory.PatternFactory

class AssignmentNodeBuilder : ASTNodeBuilder {
    override val formula: String = "IdentifierToken AssignationToken ExpressionNode SemicolonToken"

    override fun generate(tokens: List<Token>, parser: Parser): ASTNode {
        return AssignmentNode(
            type = "AssignmentNode",
            location = tokens[0].location,
            value = ExpressionNodeBuilder().generate(tokens.subList(2, tokens.size - 1), parser) as ExpressionNode,
            identifier = IdentifierNodeBuilder().generate(tokens.subList(0, 1), parser) as IdentifierNode
        )
    }

    override fun checkFormula(tokensString: String): Boolean {
        val expressionPattern = PatternFactory.getExpressionPattern()
        val pattern = "^IdentifierToken\\s*AssignationToken\\s*$expressionPattern\\s*SemicolonToken$"
        return Regex(pattern).matches(tokensString)
    }
}
