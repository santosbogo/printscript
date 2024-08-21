package org.semanticanalysis.semanticchecks

import org.astnode.ASTNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.statementnode.VariableDeclarationNode

class VariableDeclarationCheck : SemanticCheck {
    override fun check(node: ASTNode, symbolTable: MutableMap<String, LiteralValue>) {
        // check that the variable being declared is not already declared
        if (node.type == "VariableDeclarationNode") {
            val variableDeclarationNode = node as VariableDeclarationNode
            val variableIdentifier = variableDeclarationNode.identifier
            if (symbolTable.containsKey(variableIdentifier.name)) {
                // caso donde ya existe la variable
                throw Exception("Variable $variableIdentifier ya fue declarada")
            }
        }
    }
}
