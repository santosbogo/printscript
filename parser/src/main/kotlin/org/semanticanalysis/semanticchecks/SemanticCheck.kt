package org.semanticanalysis.semanticchecks

import org.astnode.ASTNode

interface SemanticCheck {
    fun check(node: ASTNode, symbolTable: MutableMap<String, Any>)
}
