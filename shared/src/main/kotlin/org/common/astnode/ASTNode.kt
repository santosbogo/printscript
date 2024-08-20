package org.common.astnode

import org.common.Location
import org.common.astnode.astnodevisitor.ASTNodeVisitor
import org.common.astnode.astnodevisitor.types.VisitorResult

interface ASTNode {
    val type: String
    val location: Location
    fun accept(visitor: ASTNodeVisitor): VisitorResult
}
