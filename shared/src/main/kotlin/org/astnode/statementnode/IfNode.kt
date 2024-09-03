package org.astnode.statementnode

import org.Location
import org.astnode.ASTNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.VisitorResult
import org.astnode.expressionnode.BooleanExpressionNode

class IfNode(
    override val type: String,
    override val location: Location,
    val boolean: BooleanExpressionNode,
    val ifStatements: List<ASTNode>
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor): VisitorResult {
        return visitor.visit(this)
    }
}

class ElseNode(
    override val type: String,
    override val location: Location,
    val elseStatements: List<ASTNode>
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor): VisitorResult {
        return visitor.visit(this)
    }
}

class CompleteIfNode(
    override val type: String,
    override val location: Location,
    val ifNode: IfNode,
    val elseNode: ElseNode?
) : StatementNode {
    override fun accept(visitor: ASTNodeVisitor): VisitorResult {
        return visitor.visit(this)
    }
}
