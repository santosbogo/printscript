package org.printers

class TestPrinter : Printer {
    private val printsList = mutableListOf<String>()
    override fun print(message: String) {
        printsList.add(message)
    }

    override fun getOutput(): List<String> {
        return printsList
    }
}
