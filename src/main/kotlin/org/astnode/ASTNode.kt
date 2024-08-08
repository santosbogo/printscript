package org.astnode

import org.Location
import org.astnode.astnodevisitor.ASTNodeVisitor

interface ASTNode {
    val type: String
    val location: Location
    fun accept(visitor: ASTNodeVisitor)
}
