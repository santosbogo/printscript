package rule.ruleBuilder

import rule.rules.Rule

interface RuleBuilder {
    fun buildRule(ruleName: String, value: String): Rule
}