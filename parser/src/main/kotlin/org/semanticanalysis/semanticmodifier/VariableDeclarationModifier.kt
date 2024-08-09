package org.semanticanalysis.semanticmodifier

import org.shared.astnode.ASTNode
import org.shared.astnode.expressionnode.expressionnodevisitor.EvaluateExpressionNodeVisitor
import org.shared.astnode.statementnode.VariableDeclarationNode

class VariableDeclarationModifier : SemanticMapModifier {
    override fun modify(node: ASTNode, symbolTable: MutableMap<String, Any>): MutableMap<String, Any> {
        if (node.type == "VariableDeclaration") {
            val variableDeclarationNode = node as VariableDeclarationNode
            val variableIdentifier = variableDeclarationNode.identifier
            if (symbolTable.containsKey(variableIdentifier)) {
                throw Exception("Variable $variableIdentifier ya fue declarada")
            }
            // make new symbol table add it
            val newSymbolTable = symbolTable.toMutableMap()
            newSymbolTable[variableIdentifier] = node.init.accept(EvaluateExpressionNodeVisitor())
            return newSymbolTable
        }
        return symbolTable
    }
}
