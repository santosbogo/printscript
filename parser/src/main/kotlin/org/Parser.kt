package org

import org.astnode.ASTNode
import org.astnode.ProgramNode
import org.semanticanalysis.SemanticAnalyzer
import org.semanticanalysis.SemanticAnalyzerFactory

class Parser(
    private val astGenerator: ASTGenerator = ASTGeneratorFactory().createDefaultASTGenerator(),
    private val semanticAnalyzer: SemanticAnalyzer = SemanticAnalyzerFactory().createDefaultSemanticAnalyzer()
) {
    fun parse(tokens: List<Token>): ParserResult {
        val result = ParserResult()
        semanticAnalyzer.reset()

        val statements = ArrayList<ASTNode>()
        val buffer = ArrayList<Token>()

        for (token in tokens) {
            buffer.add(token)
            if (token.type == "SemicolonToken") {
                try {
                    val node: ASTNode = astGenerator.generate(buffer)
                    try {
                        semanticAnalyzer.analyze(node)
                        statements.add(node)
                    } catch (e: Exception) {
                        result.addError("Semantic error: ${e.message}")
                    }
                } catch (e: Exception) {
                    result.addError("Syntactic error: ${e.message}")
                }
                buffer.clear()
            }
        }

        if (buffer.isNotEmpty()) {
            result.addError("Unexpected end of input. Missing semicolon at the end of the file.")
        }

        if (!result.hasErrors()) {
            result.programNode = ProgramNode(
                type = "ProgramNode",
                location = Location(1, 1),
                statements = statements
            )
        }

        return result
    }
}
