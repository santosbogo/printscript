package org

import org.astnode.ASTNode
import org.interpretervisitors.InterpreterVisitor
import org.iterator.PrintScriptIterator

class Interpreter(private val visitor: InterpreterVisitor, private var nodeIterator: PrintScriptIterator<ASTNode>) {
    fun interpret(): InterpreterResult {
        while (nodeIterator.hasNext()) {
            try {
                val node = nodeIterator.next()!!
                visitor.visit(node)
            } catch (e: Exception) {
                return InterpreterResult(visitor.printer, listOf(e.message ?: "Unknown error"))
            }
        }
        return InterpreterResult(visitor.printer, emptyList())
    }
}
