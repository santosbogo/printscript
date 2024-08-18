package org

import rules.Rule
import rules.SpaceBeforeColon
import netscape.javascript.JSObject
import org.common.astnode.ProgramNode
import org.common.astnode.astnodevisitor.ASTNodeVisitor
import rules.SpaceAroundEquals

class Formatter(private val node: ProgramNode, json : JSObject, private val visitor: ASTNodeVisitor = FormatterVisitor()) {
    private val rules = RulesFactory().createRules(json)

    fun format(): String {
        var result : String = ""
        node.statements.forEach { // Que el visitor guarde el resultado
            visitor.visit(it)
        }
        return result
    }
}

class RulesFactory {

    fun createRules(json: JSObject): List<Rule> {
        val rules = mutableListOf<Rule>()
        // TODO("Hacer con un diccionario")
        if (json.getMember("space_before_colon") as Boolean) {
            rules.add(SpaceBeforeColon())
        } else if (json.getMember("space_around_equals") as Boolean) {
            rules.add(SpaceAroundEquals())
        }

        return rules
    }
}
