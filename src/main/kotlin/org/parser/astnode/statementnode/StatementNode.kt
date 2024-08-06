package org.parser.astnode.statementnode

import org.utils.Location
import org.parser.astnode.ASTNode
import org.parser.astnode.astnodevisitor.ASTNodeVisitor
import org.parser.astnode.expressionnode.ExpressionNode

interface StatementNode: ASTNode {
    override val type: String;
    override val location: Location;
    val expression: ExpressionNode;
    override fun accept(visitor: ASTNodeVisitor)
}