package org.astnode.astnodevisitor

import org.astnode.ASTNode

interface ASTNodeVisitor {
    fun visit(node: ASTNode)
}
