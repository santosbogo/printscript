package org.astnode

import org.Location
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.types.VisitorResult

interface ASTNode {
    val type: String
    val location: Location
    fun accept(visitor: ASTNodeVisitor): VisitorResult
}
