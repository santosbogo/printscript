package ruleBuilder

import rules.NoSpaceAroundEquals
import rules.Rule

class NoSpaceAroundEqualsBuilder : RuleBuilder {
    override fun buildRule(ruleName: String, value: String): Rule {
        return NoSpaceAroundEquals()
    }
}
