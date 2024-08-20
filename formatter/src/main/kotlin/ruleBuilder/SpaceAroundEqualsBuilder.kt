package ruleBuilder

import rules.Rule
import rules.SpaceAroundEquals

class SpaceAroundEqualsBuilder : RuleBuilder {
    override fun buildRule(ruleName: String, value: String): Rule {
        return SpaceAroundEquals()
    }
}
