package rules

class NewLineAfterBrace : Rule {
    override val name: String = "NewLineAfterBrace"

    override fun applyRule(input: String): String {
        val result = input
            .replace(Regex("\\{(?!\\n)"), "{\n")
            .replace(Regex("}(?!\\n)"), "}\n")
        return result
    }
}
