package org

import org.astnode.ASTNode
import org.astnode.astnodevisitor.ASTNodeVisitor
import org.astnode.astnodevisitor.InterpreterVisitor

class Interpreter(private val visitor: ASTNodeVisitor = InterpreterVisitor()) {
    fun interpret(node: ASTNode) {
        visitor.visit(node)
    }
}
