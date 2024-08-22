package test.kotlin

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import java.io.File

class TestReader {

    fun readTokens(path: String): Quadruple<String, List<String>, Boolean, JsonObject> {
        val content = File(path).readText()
        val parts = content.split("#####")
        val codePart = parts[0].trim()
        val solutionPart = parts[1].trim().split("\n")
        val shouldSucceed = parts[2].trim().toBoolean()
        val json = getJson(parts[3].trim())
        return Quadruple(codePart, solutionPart, shouldSucceed, json)
    }

    private fun getJson(input: String): JsonObject {
        return Json.parseToJsonElement(input).jsonObject
    }
}

data class Quadruple<A, B, C, D>(
    val codePart: A,
    val solutionPart: B,
    val shouldSucceed: C,
    val json: D
)