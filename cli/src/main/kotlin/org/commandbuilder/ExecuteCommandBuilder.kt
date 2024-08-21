package org.commandbuilder

import org.Interpreter
import org.Lexer
import org.Parser
import org.command.Command
import org.command.ExecuteCommand

class ExecuteCommandBuilder(
    private val lexer: Lexer,
    private val parser: Parser,
    private val interpreter: Interpreter
) : CommandBuilder {
    override fun build(input: String): Command {
        return ExecuteCommand(input, lexer, parser, interpreter)
    }
}
