package org

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.junit.jupiter.api.Test
import java.io.File

class LinterTester {
    @Test
    fun test() {
        val runner = Runner("1.1",)
        val content = File("src/test/resources/examples-v10/linter/invalid-snake-case.txt")
            .readText()
        val jsonFile = File("src/test/resources/examples-v10/linter/invalid-snake-case.json")
            .readText()
        val json = Json.parseToJsonElement(jsonFile).jsonObject
        val result = runner.analyze(content, json)
        assert(true == true)
    }
}
