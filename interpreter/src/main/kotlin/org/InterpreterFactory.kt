package org

import org.inputers.CliInputProvider
import org.inputers.InputProvider
import org.inputers.NoInputProvider
import org.interpretervisitors.InterpreterVisitorV10
import org.interpretervisitors.InterpreterVisitorV11
import org.printers.CliPrinter
import org.printers.TestPrinter

object InterpreterFactory {
    fun createRunnerInterpreter(version: String, inputProvider: InputProvider): Interpreter {
        return when (version) {
            "1.0" -> createTestInterpreterV10()
            "1.1" -> createTestInterpreterV11(inputProvider)
            else -> throw IllegalArgumentException("Unsupported version: $version")
        }
    }

    fun createCliInterpreterV10(): Interpreter {
        return Interpreter(
            InterpreterVisitorV10(
                inputProvider = CliInputProvider(),
                printer = CliPrinter()
            )
        )
    }

    fun createTestInterpreterV10(): Interpreter {
        return Interpreter(
            InterpreterVisitorV10(
                inputProvider = NoInputProvider(),
                printer = TestPrinter()
            )
        )
    }

    fun createCliInterpreterV11(): Interpreter {
        return Interpreter(
            InterpreterVisitorV11(
                inputProvider = CliInputProvider(),
                printer = CliPrinter()
            )
        )
    }

    fun createTestInterpreterV11(inputProvider: InputProvider): Interpreter {
        return Interpreter(
            InterpreterVisitorV11(
                inputProvider = inputProvider,
                printer = TestPrinter()
            )
        )
    }
}
