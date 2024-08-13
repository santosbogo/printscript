package org.astnodebuilder

import org.common.Token
import org.shared.astnode.ASTNode

interface ASTNodeBuilder {
    val formula: String
    fun generate(tokens: List<Token>): ASTNode
}
