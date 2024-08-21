package rules

class SpaceAroundEquals : Rule {
    override val name = "space_around_equals"
    override fun applyRule(input: String): String {
        return input.replace(Regex("(?!\\s)=(?!\\s)"), " = ")
    }
}
