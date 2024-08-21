package org.astnodebuilder

import org.Location
import org.Token
import org.astnode.ASTNode
import org.astnode.expressionnode.IdentifierNode

class IdentifierNodeBuilder : ASTNodeBuilder {
    override val formula: String = "IdentifierToken"

    override fun generate(tokens: List<Token>): ASTNode {
        if (tokens.size == 3) {
            return IdentifierNode(
                type = "IdentifierNode",
                location = tokens[0].location,
                name = tokens[0].value,
                dataType = tokens[2].value
            )
        }

        return IdentifierNode(
            type = "IdentifierNode",
            location = tokens[0].location,
            name = tokens[0].value,
            dataType = ""
        )
    }

    override fun checkFormula(tokensString: String): Boolean {
        return Regex("IdentifierToken").matches(tokensString)
    }

    companion object {
        fun generateNodeFromValue(value: String, location: Location): IdentifierNode {
            return IdentifierNode(
                type = "IdentifierNode",
                location = location,
                name = value,
                dataType = ""
            )
        }
    }
}
