package org

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands

class Cli : CliktCommand() {
    override fun run() = Unit
}

fun main(args: Array<String>) = Cli()
    .subcommands(Analyze(), Format(), Validate(), Execute())
    .main(args)
