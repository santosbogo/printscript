package org

import org.printers.Printer

class TestPrinter : Printer {
    val printsList = mutableListOf<String>()
    override fun print(message: String) {
        printsList.add(message)
    }
}
