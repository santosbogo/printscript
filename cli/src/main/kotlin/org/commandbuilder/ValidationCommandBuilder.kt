package org.commandbuilder

import org.command.Command
import org.command.ValidationCommand

class ValidationCommandBuilder: CommandBuilder {
    override fun build(input: String): Command {
        return ValidationCommand(input)
    }
}