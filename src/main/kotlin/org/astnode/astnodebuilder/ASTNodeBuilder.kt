package org.astnode.astnodebuilder

import org.Token
import org.astnode.ASTNode

interface ASTNodeBuilder {
    val formula: String
    fun generate(tokens: List<Token>): ASTNode
}
