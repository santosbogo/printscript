package org

import org.inputers.InputProvider
import java.util.Queue

class TestInputProvider(private val messages: Queue<String>) : InputProvider {

    override fun input(): String {
        return messages.poll()
    }
}
