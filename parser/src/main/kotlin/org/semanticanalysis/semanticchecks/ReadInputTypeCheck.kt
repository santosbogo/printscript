package org.semanticanalysis.semanticchecks

import org.astnode.ASTNode
import org.astnode.expressionnode.LiteralValue
import org.astnode.expressionnode.ReadInputNode

class ReadInputTypeCheck : SemanticCheck {
    override fun check(node: ASTNode, symbolTable: MutableMap<String, Pair<String, LiteralValue>>) {
        // si el mensaje pasado no es de tipo 'string', rompe.
        if (node is ReadInputNode) {
            if (node.getType(symbolTable) != "string") {
                throw Exception("ReadInputNode message must be of type string")
            }
        }
    }
}
