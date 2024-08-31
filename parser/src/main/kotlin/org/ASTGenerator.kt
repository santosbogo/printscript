package org

import org.astnode.ASTNode
import org.astnodebuilder.ASTNodeBuilder
import org.astnodebuilder.AssignmentNodeBuilder
import org.astnodebuilder.ExpressionNodeBuilder
import org.astnodebuilder.IdentifierNodeBuilder
import org.astnodebuilder.IfNodeBuilder
import org.astnodebuilder.PrintNodeBuilder
import org.astnodebuilder.VariableDeclarationNodeBuilder

class ASTGenerator(private val builders: List<ASTNodeBuilder>) {
    fun generate(buffer: ArrayList<Token>): ASTNode {
        for (builder in builders) {
            if (builder.checkFormula(getFormula(buffer))) {
                return builder.generate(buffer)
            }
        }

        throw Exception(
            "Invalid statement at ${buffer.first().location}." +
                "No formula matches the tokens: ${buffer.joinToString(" ") { it.type }}"
        )
    }

    private fun getFormula(buffer: ArrayList<Token>): String {
        return buffer.joinToString(" ") { it.type }
    }
}

class ASTGeneratorFactory {
    fun createDefaultASTGenerator(): ASTGenerator {
        return ASTGenerator(
            listOf(
                VariableDeclarationNodeBuilder(),
                PrintNodeBuilder(),
                AssignmentNodeBuilder(),
                ExpressionNodeBuilder(),
                IdentifierNodeBuilder(),
                IfNodeBuilder()
            )
        )
    }
}
