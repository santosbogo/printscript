package org.parser.semanticanalysis.semanticchecks

import org.common.astnode.ASTNode

interface SemanticCheck {
    fun check(node: ASTNode, symbolTable: MutableMap<String, Any>)
}
