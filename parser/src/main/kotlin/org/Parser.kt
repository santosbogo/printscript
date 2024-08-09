package org.parser

import org.shared.Token
import org.shared.astnode.ASTGenerator
import org.shared.astnode.ASTNode
import org.semanticanalysis.SemanticAnalyzer
import org.parser.semanticanalysis.SemanticAnalyzerFactory

class Parser(private val semanticAnalyzer: SemanticAnalyzer = SemanticAnalyzerFactory().createDefaultSemanticAnalyzer()) {
    fun parse(tokens: List<Token>): ArrayList<ASTNode> {
        val statements = ArrayList<ASTNode>()
        val buffer = ArrayList<Token>()
        val identifiers = HashMap<String, String>()

        for (token in tokens) {
            buffer.add(token)
            if (token.type == "SemicolonToken") {
                val node: ASTNode = ASTGenerator().generate(buffer)
                semanticAnalyzer.analyze(node) // Run semantic analysis on each node. TODO the idea is to save a result to track all the errors that happen.
                statements.add(node)
                buffer.clear()
            }
        }

        if (buffer.isNotEmpty()) {
            // If the buffer is not empty, it means the last statement is missing a semicolon.
            throw Exception("Unexpected end of input. Missing semicolon at the end of the file.")
        }

        return statements // Should instead the ProgramNode (root of AST)
    }
}
