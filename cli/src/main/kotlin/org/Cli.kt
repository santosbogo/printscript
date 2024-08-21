package org

import org.commandbuilder.CommandBuilder

class Cli(private val commandBuilderMap: Map<String, CommandBuilder>) {
    fun run(input: String) {
        val key = input.split(" ")[0] // <command> <file> <args>
        val command = commandBuilderMap[key]?.build(input) ?: throw IllegalArgumentException("Command not found")
        command.execute()
    }
}
