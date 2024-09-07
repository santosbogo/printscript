package org

import org.astnode.ASTNode
import org.interpretervisitors.InterpreterVisitor

class Interpreter(private val visitor: InterpreterVisitor, private val nodeIterator: Iterator<ASTNode>) {
    fun interpret(): InterpreterResult {
        while (nodeIterator.hasNext()) {
            try {
                val node = nodeIterator.next()
                visitor.visit(node)
            } catch(e: Exception) {
                throw Exception(e.message)
            }

        }
        // el printer dentro del visitor tiene el output. cuando itero lo hago con el mismo visitor.
        return InterpreterResult(visitor.printer.getOutput())
    }
}
