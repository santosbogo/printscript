package rules

class SpaceAfterAndBeforeOperators: Rule {
    override val name: String = "SpaceAfterAndBeforeOperators"

    override fun applyRule(input: String): String {
        return input.replace(Regex("([+\\-*/])"), " $1 ")
    }
}