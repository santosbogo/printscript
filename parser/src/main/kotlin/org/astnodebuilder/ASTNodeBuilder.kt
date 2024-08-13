package org.astnodebuilder

import org.shared.Token
import org.shared.astnode.ASTNode

interface ASTNodeBuilder {
    val formula: String
    fun generate(tokens: List<Token>): ASTNode
    fun checkFormula(tokensString: String): Boolean
}
