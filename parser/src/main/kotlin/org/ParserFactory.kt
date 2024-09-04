package org

import org.astnodebuilder.VariableDeclarationNodeBuilder
import org.astnodebuilder.PrintNodeBuilder
import org.astnodebuilder.AssignmentNodeBuilder
import org.astnodebuilder.ExpressionNodeBuilder
import org.astnodebuilder.IdentifierNodeBuilder
import org.astnodebuilder.IfNodeBuilder
import org.semanticanalysis.semanticchecks.SemanticCheck
import org.astnodebuilder.expressions.ExpressionsNodeBuilderFactory
import org.semanticanalysis.SemanticAnalyzer
import org.semanticanalysis.semanticchecks.AssignmentKindCheck
import org.semanticanalysis.semanticchecks.AssignmentTypeCheck
import org.semanticanalysis.semanticchecks.VariableDeclarationCheck
import org.semanticanalysis.semanticchecks.VariableDeclarationTypeCheck
import org.structures.IfElseStructure

object ParserFactory {
    fun createParserV10(): Parser {
        return Parser(
            astGenerator = createASTGeneratorV10(),
            semanticAnalyzer = createSemanticAnalyzerV10(),
            supportedStructures = emptyList()
        )
    }

    fun createParserV11(): Parser {
        return Parser(
            astGenerator = createASTGeneratorV11(),
            semanticAnalyzer = createSemanticAnalyzerV11(),
            supportedStructures = listOf(IfElseStructure())
        )
    }

    private fun createASTGeneratorV10(): ASTGenerator {
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

    private fun createASTGeneratorV11(): ASTGenerator {
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

    private fun createSemanticAnalyzerV10(): SemanticAnalyzer {
        val checks: List<SemanticCheck> = listOf(
            VariableDeclarationCheck(),
            AssignmentTypeCheck(),
            VariableDeclarationTypeCheck(),
        )
        return SemanticAnalyzer(checks)
    }

    private fun createSemanticAnalyzerV11(): SemanticAnalyzer {
        val checks: List<SemanticCheck> = listOf(
            VariableDeclarationCheck(),
            AssignmentTypeCheck(),
            VariableDeclarationTypeCheck(),
            AssignmentKindCheck(),
        )
        return SemanticAnalyzer(checks)
    }
}
