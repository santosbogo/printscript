package rule.rules

class SpaceBeforeColon : Rule {
    override val name = "space_before_colon"

    override fun applyRule(input: String): String {
        return input.replace(":", " :")
    }
}