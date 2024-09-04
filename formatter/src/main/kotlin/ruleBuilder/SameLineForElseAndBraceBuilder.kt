package ruleBuilder

import rules.Rule

class SameLineForElseAndBraceBuilder : RuleBuilder {
    override fun buildRule(ruleName: String, value: String): Rule {
        return rules.SameLineForElseAndBrace()
    }
}
