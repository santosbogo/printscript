package org.astnodebuilder.expressions

import org.astnodebuilder.ASTNodeBuilder

class ExpressionsNodeBuilderFactory {
    fun createV10(): List<ASTNodeBuilder> {
        return listOf(
            BinaryExpressionNodeBuilder(),
        )
    }

    fun createV11(): List<ASTNodeBuilder> {
        return listOf(
            BinaryExpressionNodeBuilder(),
            ReadEnvNodeBuilder(),
            ReadInputNodeBuilder()
        )
    }
}
