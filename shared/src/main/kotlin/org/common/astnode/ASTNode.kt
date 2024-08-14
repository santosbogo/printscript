package org.common.astnode

import org.common.Location
import org.common.astnode.astnodevisitor.types.VisitorResult
import org.common.astnode.astnodevisitor.ASTNodeVisitor

interface ASTNode {
    val type: String
    val location: Location
    fun accept(visitor: ASTNodeVisitor): VisitorResult
}
