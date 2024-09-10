package org

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream
import java.io.StringReader

class LinterTester {
    @Test
    fun lint() {
        val filePath = File("src/test/resources/examples-v10/lint/examples/valid-no-rules/main.ps")
        val rulesPath = File("src/test/resources/examples-v10/lint/examples/valid-no-rules/config.json").readText()

        val reader = TestReader()
        // val (code, expectedWarnings, shouldSucceed) = reader.readTokens(filePath.path)
        val code = filePath.readText()

        val rulesContent = Json.parseToJsonElement(rulesPath).jsonObject
        val runner = Runner("1.0", StringReader(code))
        val linterResult = runner.analyze(rulesContent)

        val shouldSucceed = true
        val expectedWarnings = listOf<String>()
        compareResults(code, linterResult, expectedWarnings, shouldSucceed)
    }

    private fun compareResults(
        code: String,
        reportList: RunnerResult.Analyze,
        expectedWarnings: List<String>,
        shouldSucceed: Boolean,
    ) {
        val warnings = reportList.warningsList
        val errors = reportList.errorsList

        // agrega caso de que errors este empty y debería haber fallado.
        if (!shouldSucceed) {
            assert(false) { "Expected an error but test passed for code $code" }
        }

        // voy chequeando q los warnings q me devuelve el linter sean los q espero.
        expectedWarnings.forEachIndexed() {
            index, expectedWarning ->
            assert(warnings[index] == expectedWarning) {
                "Mismatch in code \"$code\": expected \"$expectedWarning\", found \"${warnings[index]}\""
            }
        }
    }
}
