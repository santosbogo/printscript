package org

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import java.io.File

class LinterTester {
    fun lint() {
        val filePath = File("src/test/resources/test1/test1.txt")

        val reader = TestReader()
        val (code, expectedWarnings, shouldSucceed) = reader.readTokens(filePath.path)

        val rulesPath = "src/test/resources/test1/test1.json"
        val rulesContent = Json.parseToJsonElement(rulesPath).jsonObject
        {}
        val runner = Runner("1.1")
        val linterResult = runner.analyze(code, rulesContent)

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
