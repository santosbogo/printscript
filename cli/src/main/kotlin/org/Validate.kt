package org

import com.github.ajalt.clikt.core.CliktCommand

class Validate : CliktCommand() {
    override fun run() {
        echo("Validating project")
    }
}
