package rules

class SpaceAroundEquals : Rule {

    override fun applyRule(input: String): String {
        return input.replace("=", " = ")
    }
}