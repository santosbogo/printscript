package org

import org.astnode.ASTNode

class Interpreter(private val visitor: InterpreterVisitor = InterpreterVisitor()) {
    fun interpret(node: ASTNode): InterpreterResult {
        visitor.visit(node)
        return InterpreterResult(visitor.printsList)
    }
}
