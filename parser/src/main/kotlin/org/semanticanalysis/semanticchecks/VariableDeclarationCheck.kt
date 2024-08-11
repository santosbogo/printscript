package org.semanticanalysis.semanticchecks

import org.shared.astnode.ASTNode
import org.shared.astnode.statementnode.VariableDeclarationNode
import org.parser.semanticanalysis.semanticchecks.SemanticCheck

class VariableDeclarationCheck : SemanticCheck {
    override fun check(node: ASTNode, symbolTable: MutableMap<String, Any>) {
        // check that the variable being declared is not already declared
        if (node.type == "VariableDeclaration") {
            val variableDeclarationNode = node as VariableDeclarationNode
            val variableIdentifier = variableDeclarationNode.identifier
            if (symbolTable.containsKey(variableIdentifier.name)) {
                // caso donde ya existe la variable
                throw Exception("Variable $variableIdentifier ya fue declarada")
            }
        }
    }
}
