package org.commandbuilder

import org.Lexer
import org.Parser
import org.command.Command
import org.command.FormattingCommand

class FormattingCommandBuilder(
    private val lexer: Lexer,
    private val parser: Parser
) : CommandBuilder {
    override fun build(input: String): Command {
        return FormattingCommand(input, lexer, parser)
    }
}
