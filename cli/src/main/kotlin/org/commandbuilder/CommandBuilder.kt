package org.commandbuilder

import org.command.Command

interface CommandBuilder {
    fun build(input: String): Command
}