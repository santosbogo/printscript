package org

import org.common.astnode.ASTNode
import org.common.astnode.astnodevisitor.ASTNodeVisitor
import org.common.astnode.astnodevisitor.InterpreterVisitor

class Interpreter(private val visitor: ASTNodeVisitor = InterpreterVisitor()) {
    fun interpret(node: ASTNode) {
        visitor.visit(node)
    }
}
