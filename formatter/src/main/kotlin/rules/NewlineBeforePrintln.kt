package rules

class NewlineBeforePrintln(private val shift: Int?) : Rule {
    override val name: String = "NewlineBeforePrintln"

    override fun applyRule(input: String): String {
        val newlines = "\n".repeat(shift ?: 1)
        return input.replace("println", "${newlines}println")
    }
}