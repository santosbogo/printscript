package org.commandbuilder

import org.command.Command
import org.command.ExecuteCommand

class ExecuteCommandBuilder: CommandBuilder {
    override fun build(input: String): Command {
        return ExecuteCommand(input)
    }
}