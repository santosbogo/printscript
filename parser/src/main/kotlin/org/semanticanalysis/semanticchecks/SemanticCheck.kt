package org.semanticanalysis.semanticchecks

import org.astnode.ASTNode
import org.astnode.expressionnode.LiteralValue

interface SemanticCheck {
    fun check(node: ASTNode, symbolTable: MutableMap<String, LiteralValue>)
}
