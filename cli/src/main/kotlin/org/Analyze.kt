package org

import com.github.ajalt.clikt.core.CliktCommand

class Analyze : CliktCommand() {
    override fun run() {
        echo("Analyzing project")
    }
}
