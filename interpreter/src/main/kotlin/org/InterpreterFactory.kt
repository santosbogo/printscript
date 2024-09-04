package org

import org.interpretervisitors.InterpreterVisitorV10
import org.interpretervisitors.InterpreterVisitorV11

object InterpreterFactory {
    fun createInterpreterV10(): Interpreter {
        return Interpreter(InterpreterVisitorV10())
    }

    fun createInterpreterV11(): Interpreter {
        return Interpreter(InterpreterVisitorV11())
    }
}
