package ruleBuilder

import rules.NumberOfSpacesIndentation
import rules.Rule

class NumberOfSpacesIndentationBuilder : RuleBuilder {

    override fun buildRule(ruleName: String, value: String): Rule {
        return NumberOfSpacesIndentation(value.toInt())
    }
}
