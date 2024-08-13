package org.astnodebuilder

import org.shared.Token
import org.shared.astnode.ASTNode
import org.shared.astnode.expressionnode.ExpressionNode
import org.common.astnode.statementnode.VariableDeclarationNode

class VariableDeclarationNodeBuilder : ASTNodeBuilder {
    override val formula: String =
        "DeclarationToken IdentifierToken ColonToken TypeToken AssignationToken ExpressionNode SemicolonToken"
    override fun generate(tokens: List<Token>): ASTNode {
        return VariableDeclarationNode(
            type = "VariableDeclarationNode",
            location = tokens[0].location,
            identifier = IdentifierNodeBuilder.generateNodeFromValue(tokens[1].value, tokens[0].location),
            init = ExpressionNodeBuilder().generate(tokens.subList(5, tokens.size - 1)) as ExpressionNode,
            kind = tokens[0].value
        )
    }

    override fun checkFormula(tokensString: String): Boolean {
        val expressionPattern = "(IdentifierToken|StringToken|NumberToken|PlusToken|MinusToken|MultiplyToken|DivisionToken)*"
        val pattern = "DeclarationToken IdentifierToken ColonToken TypeToken AssignationToken $expressionPattern SemicolonToken"
        return Regex(pattern).matches(tokensString)
    }
}
