package rules

class NewlineAfterPrintln(private val shift: Int?) : Rule {
    override val name: String = "NewlineAfterPrintln"

    override fun applyRule(input: String): String {
        val newlines = "\n".repeat(shift ?: 1)

        val regex = """(println\([^)]*\);)""".toRegex()

        return input.replace(regex) {
            val fullMatch = it.value
            if (fullMatch.contains("\"")) {
                "$fullMatch$newlines"
            } else {
                "$fullMatch$newlines"
            }
        }
    }
}
