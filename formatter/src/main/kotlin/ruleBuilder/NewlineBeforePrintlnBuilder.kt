package ruleBuilder

import rules.NewlineBeforePrintln
import rules.Rule

class NewlineBeforePrintlnBuilder: RuleBuilder {
    override fun buildRule(ruleName: String, value: String): Rule {
        return NewlineBeforePrintln(value.toInt())
    }
}