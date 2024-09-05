package rules

class SpaceAroundEquals : Rule {
    override val name = "enforce-spacing-around-equals"
    override fun applyRule(input: String): String {
        return input.replace(Regex("\\s*(=)\\s*"), " $1 ")
    }
}
