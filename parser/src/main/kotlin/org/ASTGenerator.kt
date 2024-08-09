package org

import org.astnodebuilder.ASTNodeBuilder
import org.astnodebuilder.AssignmentNodeBuilder
import org.astnodebuilder.ExpressionNodeBuilder
import org.astnodebuilder.IdentifierNodeBuilder
import org.astnodebuilder.PrintNodeBuilder
import org.astnodebuilder.VariableDeclarationNodeBuilder
import org.shared.Token
import org.shared.astnode.ASTNode

class ASTGenerator(private val builders: List<ASTNodeBuilder>) {
    fun generate(buffer: ArrayList<Token>): ASTNode {
        for (builder in builders) {
            if (builder.formula == getFormula(buffer)) {
                return builder.generate(buffer)
            }
        }

        throw Exception("Syntax error: Invalid statement at ${buffer.first().location}." +
                "No formula matches the tokens: ${buffer.joinToString(" ") { it.type }}")
    }

    private fun getFormula(buffer: ArrayList<Token>): String {
        return buffer.joinToString(" ") { it.type }
    }
}

class ASTGeneratorFactory {
    fun createDefaultASTGenerator(): ASTGenerator {
        return ASTGenerator(listOf(
            VariableDeclarationNodeBuilder(),
            PrintNodeBuilder(),
            AssignmentNodeBuilder(),
            ExpressionNodeBuilder(),
            IdentifierNodeBuilder()
        ))
    }
}