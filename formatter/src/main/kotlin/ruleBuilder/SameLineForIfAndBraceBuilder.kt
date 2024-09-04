package ruleBuilder

import rules.Rule
import rules.SameLineForIfAndBrace

class SameLineForIfAndBraceBuilder : RuleBuilder {
    override fun buildRule(ruleName: String, value: String): Rule {
        return SameLineForIfAndBrace()
    }
}
