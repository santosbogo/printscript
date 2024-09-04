package rules

class SameLineForElseAndBrace : Rule {
    override val name: String = "SameLineForElseAndBrace"

    override fun applyRule(input: String): String {
        return input.replace(Regex("}\\s*\\n\\s*else"), "} else")
    }
}
