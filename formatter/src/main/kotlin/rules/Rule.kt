package rules

interface Rule {
    fun applyRule(input: String) : String
}