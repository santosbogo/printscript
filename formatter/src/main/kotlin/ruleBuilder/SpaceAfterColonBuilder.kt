package ruleBuilder
import rules.Rule
import rules.SpaceAfterColon

class SpaceAfterColonBuilder : RuleBuilder {
    override fun buildRule(ruleName: String, value: String): Rule {
        return SpaceAfterColon()
    }
}
