package org.printers

interface Printer {
    fun print(message: String)
    fun getOutput(): List<String>
}
