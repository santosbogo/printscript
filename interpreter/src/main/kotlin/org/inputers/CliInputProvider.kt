package org.inputers

class CliInputProvider : InputProvider {
    override fun input(): String {
        val value = readln()
        return value
    }
}
