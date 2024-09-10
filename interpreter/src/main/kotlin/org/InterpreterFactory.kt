package org

import org.astnode.ASTNode
import org.inputers.CliInputProvider
import org.inputers.InputProvider
import org.inputers.NoInputProvider
import org.interpretervisitors.InterpreterVisitorV10
import org.interpretervisitors.InterpreterVisitorV11
import org.iterator.PrintScriptIterator
import org.printers.CliPrinter
import org.printers.TestPrinter

object InterpreterFactory {
    fun createRunnerInterpreter(version: String, inputProvider: InputProvider, nodeIterator: PrintScriptIterator<ASTNode>): Interpreter {
        return when (version) {
            "1.0" -> createTestInterpreterV10(nodeIterator)
            "1.1" -> createTestInterpreterV11(inputProvider, nodeIterator)
            else -> throw IllegalArgumentException("Unsupported version: $version")
        }
    }

    fun createCliInterpreterV10(nodeIterator: PrintScriptIterator<ASTNode>): Interpreter {
        return Interpreter(
            InterpreterVisitorV10(
                inputProvider = CliInputProvider(),
                printer = CliPrinter()
            ),
            nodeIterator
        )
    }

    fun createTestInterpreterV10(nodeIterator: PrintScriptIterator<ASTNode>): Interpreter {
        return Interpreter(
            InterpreterVisitorV10(
                inputProvider = NoInputProvider(),
                printer = TestPrinter()
            ),
            nodeIterator
        )
    }

    fun createCliInterpreterV11(nodeIterator: PrintScriptIterator<ASTNode>): Interpreter {
        return Interpreter(
            InterpreterVisitorV11(
                inputProvider = CliInputProvider(),
                printer = CliPrinter(),
            ),
            nodeIterator
        )
    }

    fun createTestInterpreterV11(inputProvider: InputProvider, nodeIterator: PrintScriptIterator<ASTNode>): Interpreter {
        return Interpreter(
            InterpreterVisitorV11(
                inputProvider = inputProvider,
                printer = TestPrinter()
            ),
            nodeIterator,
        )
    }
}
