package ruleBuilder
import rules.Rule

interface RuleBuilder {
    fun buildRule(ruleName: String, value: String): Rule
}