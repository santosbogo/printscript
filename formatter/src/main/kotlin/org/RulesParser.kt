package org

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject

class RulesParser {
    fun parse(content: String): JsonObject {
        if (content == "") {
            return buildJsonObject {}
        }
        val theirJson: JsonObject = Json.parseToJsonElement(content).jsonObject
        val map = getMapOfTCK()

        val ourJson = buildJsonObject {
            for ((key, value) in theirJson) {
                if (key in ourFormat) { // If the key is already in our format
                    put(key, value)
                } else if (key in map) { // If the key isn't in our format, but expected
                    put(map[key]!!, value)
                }
            }
        }

        return ourJson
    }

    private fun getMapOfTCK(): Map<String, String> {
        return mapOf(
            "enforce-spacing-around-equals" to "space_around_equals",
            "enforce-no-spacing-around-equals" to "no_space_around_equals",
            "enforce-spacing-after-colon-in-declaration" to "space_after_colon",
            "enforce-spacing-before-colon-in-declaration" to "space_before_colon",
        )
    }

    private val ourFormat = listOf(
        "space_before_colon",
        "space_after_colon",
        "newline_after_println",
        "newline_before_println",
        "space_around_equals",
        "no_space_around_equals",
        "number_of_spaces_indentation",
        "same_line_for_if_brace",
        "same_line_for_else_brace"
    )
}
