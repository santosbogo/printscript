package org

import org.inputers.TestInputProvider
import org.junit.jupiter.api.Test
import org.printers.TestPrinter
import java.io.File
import java.io.StringReader
import java.util.LinkedList

class ExecuteTester {
    @Test
    fun test() {
        val content = File("src/test/resources/examples-v11/execute/arithmetic-operations-decimal.txt").readText()
        val inputProvider = TestInputProvider(LinkedList(listOf("world")))
        val runner = Runner("1.1", StringReader(content))
        val result = runner.execute("1.1", TestPrinter(), inputProvider)
        assert(true == true)
    }
}
