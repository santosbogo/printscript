package org

class Lexicon(private val tokenMap: List<Pair<String, String>>) {

    fun getToken(component: String, location: Location): Token {
        val tokenType = tokenMap.firstOrNull { (pattern, _) ->
            component.matches(Regex(pattern))
        }?.second ?: throw Exception(
            "Lexicon Error: " +
                "No token found for component: $component at $location"
        )

        return Token(
            type = tokenType,
            value = component.replace("\"", "").replace("'", ""),
            location = location
        )
    }
}
