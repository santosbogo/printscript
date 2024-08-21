package rules

class OnlyOneSpacePermited: Rule {
    override val name: String = "OnlyOneSpacePermited"

    override fun applyRule(input: String): String {
        return input.replace(Regex("[ \\t]+\n"), " ")
    }
}