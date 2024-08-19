package ruleBuilder

import rules.Rule
import rules.SpaceBeforeColon

class SpaceBeforeColonBuilder: RuleBuilder {
    override fun buildRule(ruleName: String, value: String): Rule {
        return SpaceBeforeColon()
    }
}