package org.astnodebuilder

import org.shared.Token
import org.shared.astnode.ASTNode
import org.shared.astnode.expressionnode.IdentifierNode

class IdentifierNodeBuilder: ASTNodeBuilder {
    override val formula: String = "IdentifierToken"

    override fun generate(tokens: List<Token>): ASTNode {
        return IdentifierNode(
            type = "IdentifierNode",
            location = tokens[0].location,
            name = tokens[0].value,
            dataType = "" // TODO(ver como conseguir la data type)
        )
    }
}