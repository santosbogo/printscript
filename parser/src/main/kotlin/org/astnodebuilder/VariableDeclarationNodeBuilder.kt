package org.astnodebuilder

import org.Token
import org.astnode.ASTNode
import org.astnode.expressionnode.ExpressionNode
import org.astnode.expressionnode.IdentifierNode
import org.astnode.statementnode.VariableDeclarationNode
import org.expressionfactory.PatternFactory

class VariableDeclarationNodeBuilder : ASTNodeBuilder {
    override val formula: String =
        "DeclarationToken IdentifierToken ColonToken TypeToken AssignationToken ExpressionNode SemicolonToken"
    override fun generate(tokens: List<Token>): ASTNode {
        return VariableDeclarationNode(
            type = "VariableDeclarationNode",
            location = tokens[0].location,
            identifier = IdentifierNodeBuilder().generate(tokens.subList(0, 4)) as IdentifierNode,
            init = ExpressionNodeBuilder().generate(tokens.subList(5, tokens.size - 1)) as ExpressionNode,
            kind = tokens[0].value
        )
    }

    override fun checkFormula(tokensString: String): Boolean {
        val expressionPattern = PatternFactory.getExpressionPattern()
        val pattern = "^DeclarationToken\\s*IdentifierToken\\s*ColonToken" +
            "\\s*TypeToken\\s*AssignationToken\\s*$expressionPattern\\s*SemicolonToken$"
        return Regex(pattern).matches(tokensString)
    }
}
