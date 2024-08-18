package rules

class SpaceBeforeColon : Rule {

    override fun applyRule(input: String): String {
        return input.replace(":", " :")
    }
}