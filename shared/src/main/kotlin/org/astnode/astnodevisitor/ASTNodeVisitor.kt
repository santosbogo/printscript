package org.astnode.astnodevisitor

import org.astnode.ASTNode
import org.astnode.astnodevisitor.types.VisitorResult

interface ASTNodeVisitor {
    fun visit(node: ASTNode): VisitorResult
}
