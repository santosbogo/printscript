package org

import org.astnode.ASTNode
import org.interpretervisitors.InterpreterVisitor
import org.iterator.PrintScriptIterator

class Interpreter(private val visitor: InterpreterVisitor, private var nodeIterator: PrintScriptIterator<ASTNode>) {
    fun interpret(): InterpreterResult {
        while (nodeIterator.hasNext()) {
            try {gg
                val node = nodeIterator.next()!!
                visitor.visit(node)dsgr
            } catch (e: Exception) {
                return InterpreterResult(emptyList(), listOf(e.message ?: "Unknown error"))
            }
        }
        return InterpreterResult(visitor.printer.getOutput(), emptyList())
    }
}
