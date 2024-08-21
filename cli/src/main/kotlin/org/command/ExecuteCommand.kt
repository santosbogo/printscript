package org.command

import org.Interpreter
import org.Lexer
import org.Parser

class ExecuteCommand(
    private val input: String,
    private val lexer: Lexer,
    private val parser: Parser,
    private val interpreter: Interpreter
) : Command {
    override fun execute() {
        // execute file.txt
        val file = input.split(" ")[1]
        val tokens = lexer.tokenize(file)
        val ast = parser.parse(tokens)
        interpreter.interpret(ast)
    }
}
