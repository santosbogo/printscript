package org.astnodebuilder

import org.common.Location
import org.common.Token
import org.shared.astnode.ASTNode
import org.shared.astnode.expressionnode.IdentifierNode

class IdentifierNodeBuilder: ASTNodeBuilder {
    override val formula: String = "IdentifierToken"

    override fun generate(tokens: List<Token>): ASTNode {
        return IdentifierNode(
            type = "IdentifierNode",
            location = tokens[0].location,
            name = tokens[0].value,
            dataType = "" // declarada en el semantic modifier.
        )
    }

    companion object {
        fun generateNodeFromValue(value: String, location: Location): IdentifierNode {
            return IdentifierNode(
                type = "IdentifierNode",
                location = location,
                name = value,
                dataType = "" // declarada en el semantic modifier.
            )
        }
    }
}