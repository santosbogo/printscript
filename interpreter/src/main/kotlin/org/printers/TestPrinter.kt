package org.printers

class TestPrinter : Printer {
    val printsList = mutableListOf<String>()
    override fun print(message: String) {
        printsList.add(message)
    }

    override fun getOutput(): List<String> {
        return printsList
    }
}
