package rules

class NewLineAfterSemiColon: Rule {
    override val name: String = "NewLineAfterSemiColon"

    override fun applyRule(input: String): String {
        return input.replace(";", ";\n")
    }
}