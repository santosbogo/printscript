package org.astnode.astnodebuilder

import org.Token
import org.astnode.ASTNode
import org.astnode.expressionnode.ExpressionNode
import org.astnode.statementnode.VariableDeclarationNode

class VariableDeclarationNodeBuilder() : ASTNodeBuilder {
    override val formula: String = "DeclarationToken IdentifierToken ColonToken TypeToken AssignationToken ExpressionNode SemicolonToken"
    override fun generate(tokens: List<Token>): ASTNode {
        return VariableDeclarationNode(
            type = "VariableDeclarationNode",
            location = tokens[0].location,
            identifier = tokens[1].value,
            init = ExpressionNodeBuilder().generate(tokens.subList(5, tokens.size - 2)) as ExpressionNode,
            kind = tokens[0].value
        )
    }
}
