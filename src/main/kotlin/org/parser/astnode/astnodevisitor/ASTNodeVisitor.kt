package org.parser.astnode.astnodevisitor

import org.parser.astnode.ASTNode

interface ASTNodeVisitor {
    fun visit(node: ASTNode)
}