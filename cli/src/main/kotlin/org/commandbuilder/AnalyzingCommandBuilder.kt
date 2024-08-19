package org.commandbuilder

import org.command.AnalyzingCommand
import org.command.Command

class AnalyzingCommandBuilder: CommandBuilder {
    override fun build(input: String): Command {
        return AnalyzingCommand(input)
    }
}