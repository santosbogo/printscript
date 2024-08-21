package org.commandbuilder

import org.command.Command
import org.command.FormattingCommand

class FormattingCommandBuilder : CommandBuilder {
    override fun build(input: String): Command {
        return FormattingCommand(input)
    }
}
