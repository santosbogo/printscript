package org.parser.astnode.expressionnode

import org.utils.Location
import org.parser.astnode.ASTNode
import org.parser.astnode.astnodevisitor.ASTNodeVisitor

interface ExpressionNode: ASTNode {
    override val type: String;
    override val location: Location;
    override fun accept(visitor: ASTNodeVisitor)
}