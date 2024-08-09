package org.parser.semanticanalysis.semanticchecks

import org.shared.astnode.ASTNode

interface SemanticCheck {
    fun check(node: ASTNode, symbolTable: MutableMap<String, Any>)
}
