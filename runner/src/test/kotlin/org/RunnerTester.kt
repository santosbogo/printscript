package org

import org.junit.jupiter.api.Test

class RunnerTester {

    @Test
    fun test() {
        val runner = Runner("1.0")
        val result = runner.execute("print(1 + 2)")
        println(result)
    }
}
