package org.commandbuilder

import org.Lexer
import org.Parser
import org.command.AnalyzingCommand
import org.command.Command

class AnalyzingCommandBuilder(
    private val lexer: Lexer,
    private val parser: Parser
) : CommandBuilder {
    override fun build(input: String): Command {
        return AnalyzingCommand(input, lexer, parser)
    }
}
