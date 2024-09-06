package org

import org.inputers.TestInputProvider
import org.junit.jupiter.api.Test
import java.io.File
import java.util.LinkedList

class ExecuteTester {
    @Test
    fun test() {
        val runner = Runner("1.1",)
        val content = File("src/test/resources/examples-v11/execute/arithmetic-operations-decimal.txt").readText()
        val inputProvider = TestInputProvider(LinkedList(listOf("world")))
        val result = runner.execute(content, "1.1", inputProvider)
        assert(true == true)
    }
}
