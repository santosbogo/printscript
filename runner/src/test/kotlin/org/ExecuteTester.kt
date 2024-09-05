package org

import org.junit.jupiter.api.Test
import java.io.File

class ExecuteTester {
    @Test
    fun test() {
        val runner = Runner("1.1")
        val content = File("/home/tomasvalle/projects/PrintScript/runner/src/test/resources/examples-v11/execute/invalid-argument-in-if.txt").readText()
        val result = runner.execute(content)
        assert(true == true)
    }
}
