package rules

class NewlineBeforePrintln(private val shift: Int?) : Rule {
    override val name: String = "NewlineBeforePrintln"

    override fun applyRule(input: String): String {
        return input.replace("println", "\nprintln")
    }
}
