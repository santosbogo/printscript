package org.inputers

class NoInputProvider : InputProvider {
    override fun input(): String {
        return ""
    }
}
