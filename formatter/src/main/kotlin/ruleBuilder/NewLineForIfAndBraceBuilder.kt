package ruleBuilder

import rules.NewLineForIfAndBrace
import rules.Rule

class NewLineForIfAndBraceBuilder : RuleBuilder {
    override fun buildRule(ruleName: String, value: String): Rule {
        return NewLineForIfAndBrace()
    }
}
