package org.astnodebuilder

import org.Parser
import org.Token
import org.astnode.ASTNode

interface ASTNodeBuilder {
    val formula: String
    fun generate(tokens: List<Token>, parser: Parser): ASTNode
    fun checkFormula(tokensString: String): Boolean
}
