package rules

class SpaceAfterColon : Rule {
    override val name: String = "SpaceAfterColon"

    override fun applyRule(input: String): String {
        return input.replace(":", ": ")
    }
}
