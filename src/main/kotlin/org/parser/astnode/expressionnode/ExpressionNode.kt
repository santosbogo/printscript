package org.parser.astnode.expressionnode

import org.Location
import org.parser.astnode.ASTNode
import org.parser.astnode.astnodevisitor.ASTNodeVisitor
import org.parser.astnode.expressionnode.expressionnodevisitor.EvaluateExpressionNodeVisitor
import org.parser.astnode.expressionnode.expressionnodevisitor.ExpressionNodeVisitor

interface ExpressionNode {
    val type: String;
    val location: Location;
    fun accept(visitor: ExpressionNodeVisitor): Any
}