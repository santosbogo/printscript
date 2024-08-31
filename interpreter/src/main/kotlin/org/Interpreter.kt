package org

import org.astnode.ASTNode
import org.astnode.astnodevisitor.ASTNodeVisitor

class Interpreter(private val visitor: ASTNodeVisitor = InterpreterVisitor()) {
    fun interpret(node: ASTNode) {
        visitor.visit(node)
    }
}
