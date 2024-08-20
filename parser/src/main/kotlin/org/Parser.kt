package org

import org.astnode.ASTNode
import org.astnode.ProgramNode
import org.semanticanalysis.SemanticAnalyzer
import org.semanticanalysis.SemanticAnalyzerFactory

class Parser(
    private val astGenerator: ASTGenerator = ASTGeneratorFactory().createDefaultASTGenerator(),
    private val semanticAnalyzer: SemanticAnalyzer = SemanticAnalyzerFactory().createDefaultSemanticAnalyzer()
) {
    fun parse(tokens: List<Token>): ProgramNode {
        val statements = ArrayList<ASTNode>()
        val buffer = ArrayList<Token>()

        for (token in tokens) {
            buffer.add(token)
            if (token.type == "SemicolonToken") {
                val node: ASTNode = astGenerator.generate(buffer)
                semanticAnalyzer.analyze(node) // Run semantic analysis on each node. TODO the idea is to save a result to track all the errors that happen.
                statements.add(node)
                buffer.clear()
            }
        }

        if (buffer.isNotEmpty()) {
            throw Exception("Unexpected end of input. Missing semicolon at the end of the file.")
        }

        val programNode = ProgramNode(
            type = "ProgramNode",
            location = Location(1, 1),
            statements = statements
        )
        return programNode
    }
}
