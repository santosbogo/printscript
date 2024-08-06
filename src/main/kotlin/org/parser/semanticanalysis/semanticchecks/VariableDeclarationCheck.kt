package org.parser.semanticanalysis.semanticchecks

import org.parser.astnode.ASTNode
import org.parser.astnode.expressionnode.expressionnodevisitor.EvaluateExpressionNodeVisitor
import org.parser.astnode.statementnode.VariableDeclaration

class VariableDeclarationCheck : SemanticCheck {
    override fun check(node: ASTNode, symbolTable: MutableMap<String, Any>) {
        //check that the variable being declared is not already declared
        if (node.type == "VariableDeclaration") {
            val variableDeclarationNode = node as VariableDeclaration
            val variableIdentifier = variableDeclarationNode.identifier
            if (symbolTable.containsKey(variableIdentifier)) {
                //caso donde ya existe la variable
                throw Exception("Variable $variableIdentifier ya fue declarada")
            }
            //agrego la variable a la symbolTable si todavia no existe
            symbolTable[variableIdentifier] = node.init.accept(EvaluateExpressionNodeVisitor())
        }
    }

}