package rules

interface Rule {
    val name: String
    fun applyRule(input: String): String
}
