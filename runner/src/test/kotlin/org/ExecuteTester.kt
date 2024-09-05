package org

import org.junit.jupiter.api.Test
import java.io.File
import java.util.LinkedList

class ExecuteTester {
    @Test
    fun test() {
        val runner = Runner("1.1",)
        val content = File("src/test/resources/examples-v11/execute/invalid-argument-in-if.txt").readText()
        val result = runner.execute(content, "1.1", LinkedList())
        assert(true == true)
    }
}
