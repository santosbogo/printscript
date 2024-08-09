package org.semanticanalysis.semanticmodifier

import org.shared.astnode.ASTNode

interface SemanticMapModifier {
    fun modify(node: ASTNode, symbolTable: MutableMap<String, Any>): MutableMap<String, Any>
}
