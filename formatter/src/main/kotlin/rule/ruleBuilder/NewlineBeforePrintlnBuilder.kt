package rule.ruleBuilder

import rule.rules.NewlineBeforePrintln
import rule.rules.Rule

class NewlineBeforePrintlnBuilder: RuleBuilder {
    override fun buildRule(ruleName: String, value: String): Rule {
        return NewlineBeforePrintln(value.toInt())
    }
}