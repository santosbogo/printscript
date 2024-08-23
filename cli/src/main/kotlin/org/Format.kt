package org

import com.github.ajalt.clikt.core.CliktCommand

class Format : CliktCommand() {
    override fun run() {
        echo("Formatting code")
    }
}
