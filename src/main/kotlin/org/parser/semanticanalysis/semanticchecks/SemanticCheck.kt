package org.parser.semanticanalysis.semanticchecks

import org.parser.astnode.ASTNode

interface SemanticCheck {
    fun check(node: ASTNode, symbolTable: MutableMap<String, Any>)
}
