package org

import org.inputers.CliInputProvider
import org.inputers.NoInputProvider
import org.inputers.TestInputProvider
import org.interpretervisitors.InterpreterVisitorV10
import org.interpretervisitors.InterpreterVisitorV11
import org.printers.CliPrinter
import org.printers.TestPrinter
import java.util.Queue

object InterpreterFactory {
    fun createRunnerInterpreter(version: String, input: Queue<String>): Interpreter {
        return when (version) {
            "1.0" -> createTestInterpreterV10()
            "1.1" -> createTestInterpreterV11(input)
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

    fun createTestInterpreterV11(queue: Queue<String>): Interpreter {
        return Interpreter(
            InterpreterVisitorV11(
                inputProvider = TestInputProvider(queue),
                printer = TestPrinter()
            )
        )
    }
}
