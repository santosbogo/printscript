package org.parser.astnode

import org.utils.Location
import org.parser.astnode.astnodevisitor.ASTNodeVisitor

interface ASTNode {
    val type: String;
    val location: Location;
    fun accept(visitor: ASTNodeVisitor)
}