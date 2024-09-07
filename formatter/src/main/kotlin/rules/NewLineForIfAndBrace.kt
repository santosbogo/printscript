package rules

class NewLineForIfAndBrace : Rule {
    override val name: String = "NewLineForIfAndBrace"

    override fun applyRule(input: String): String {
        return input.replace(Regex("\\bif\\b\\s*(\\(.*?\\))\\s*\\n?\\s*\\{"), "if $1\n{")
    }
}
