package rule.ruleBuilder

import rule.rules.Rule
import rule.rules.SpaceAfterColon

class SpaceAfterColonBuilder: RuleBuilder {
    override fun buildRule(ruleName: String, value: String): Rule {
        return SpaceAfterColon()
    }
}