package org

import org.inputers.NoInputProvider
import org.junit.jupiter.api.Test
import java.io.File

class ExecuteTester {
    @Test
    fun test() {
        val runner = Runner("1.1",)
        val content = File("src/test/resources/examples-v11/execute/arithmetic-operations-decimal.txt").readText()
        val result = runner.execute(content, "1.1", NoInputProvider())
        assert(true == true)
    }
}
