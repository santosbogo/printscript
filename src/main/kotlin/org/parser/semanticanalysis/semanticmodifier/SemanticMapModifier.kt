package org.parser.semanticanalysis.semanticmodifier

import org.parser.astnode.ASTNode

interface SemanticMapModifier {
    fun modify(node: ASTNode, symbolTable: MutableMap<String, Any>): MutableMap<String, Any>
}