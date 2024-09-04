package org

import org.astnode.ASTNode
import org.interpretervisitors.InterpreterVisitor
import org.interpretervisitors.InterpreterVisitorV10
import org.interpretervisitors.InterpreterVisitorV11

class Interpreter(private val visitor: InterpreterVisitor = InterpreterVisitorV11()) {
    fun interpret(node: ASTNode): InterpreterResult {
        visitor.visit(node)
        return InterpreterResult(visitor.printsList)
    }
}

class InterpreterFactory {
    fun createInterpreterV10(): Interpreter {
        return Interpreter(InterpreterVisitorV10())
    }

    fun createInterpreterV11(): Interpreter {
        return Interpreter(InterpreterVisitorV11())
    }
}
