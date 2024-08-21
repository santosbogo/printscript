package org.commandbuilder

import org.Lexer
import org.Parser
import org.command.Command
import org.command.ValidationCommand

class ValidationCommandBuilder(
    private val lexer: Lexer,
    private val parser: Parser
) : CommandBuilder {
    override fun build(input: String): Command {
        return ValidationCommand(input, lexer, parser)
    }
}
