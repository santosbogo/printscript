package org.semanticanalysis.semanticmodifier

import org.InterpreterVisitor
import org.shared.astnode.ASTNode
import org.shared.astnode.statementnode.VariableDeclarationNode

class VariableDeclarationModifier : SemanticMapModifier {
    override fun modify(node: ASTNode, symbolTable: MutableMap<String, Any>): MutableMap<String, Any> {
        if (node.type == "VariableDeclaration") {
            val variableDeclarationNode = node as VariableDeclarationNode
            val variableIdentifier = variableDeclarationNode.identifier
            variableIdentifier.dataType = variableDeclarationNode.init.type //declaro el datatype del identifier.

            if (symbolTable.containsKey(variableIdentifier.name)) {
                throw Exception("Variable $variableIdentifier ya fue declarada")
            }
            // make new symbol table add it
            val newSymbolTable = symbolTable.toMutableMap()
            newSymbolTable[variableIdentifier.name] = node.init.accept(InterpreterVisitor(newSymbolTable))
            return newSymbolTable
        }
        return symbolTable
    }
}
