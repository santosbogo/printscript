package ruleBuilder

import rules.NewlineAfterPrintln
import rules.Rule

class NewlineAfterPrintlnBuilder : RuleBuilder {
    override fun buildRule(ruleName: String, value: String): Rule {
        return NewlineAfterPrintln(value.toInt())
    }
}
