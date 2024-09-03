package org

import org.astnodebuilder.IdentifierNodeBuilder
import org.astnodebuilder.AssignmentNodeBuilder
import org.astnodebuilder.PrintNodeBuilder
import org.astnodebuilder.VariableDeclarationNodeBuilder
import org.astnodebuilder.ExpressionNodeBuilder
import org.astnodebuilder.IfNodeBuilder
import org.astnodebuilder.expressions.ExpressionsNodeBuilderFactory

class ASTGeneratorFactory {
    fun createDefaultASTGenerator(): ASTGenerator {
        return ASTGenerator(
            listOf(
                VariableDeclarationNodeBuilder(),
                PrintNodeBuilder(),
                AssignmentNodeBuilder(),
                ExpressionNodeBuilder(ExpressionsNodeBuilderFactory().createV10()),
                IdentifierNodeBuilder(),
            )
        )
    }
    fun createASTGeneratorV11(): ASTGenerator {
        return ASTGenerator(
            listOf(
                VariableDeclarationNodeBuilder(),
                PrintNodeBuilder(),
                AssignmentNodeBuilder(),
                ExpressionNodeBuilder(),
                IdentifierNodeBuilder(),
                IfNodeBuilder(),
            )
        )
    }
}
