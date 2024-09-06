package org.printers

class CliPrinter : Printer {
    override fun print(message: String) {
        println(message)
    }

    override fun getOutput(): List<String> {
        return emptyList()
    }
}
