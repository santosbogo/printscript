package org.shared.astnode

import org.shared.Location
import org.shared.astnode.astnodevisitor.ASTNodeVisitor

interface ASTNode {
    val type: String
    val location: Location
    fun accept(visitor: ASTNodeVisitor)
}
