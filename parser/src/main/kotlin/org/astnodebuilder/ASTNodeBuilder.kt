package org.astnodebuilder

import org.Token
import org.astnode.ASTNode

interface ASTNodeBuilder {
    val formula: String
    fun generate(tokens: List<Token>): ASTNode
    fun checkFormula(tokensString: String): Boolean
}
