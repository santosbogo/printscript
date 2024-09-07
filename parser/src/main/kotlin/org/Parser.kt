package org

import org.astnode.ASTNode
import org.astnode.ProgramNode
import org.semanticanalysis.SemanticAnalyzer
import org.structures.Structure

class Parser(
    private val astGenerator: ASTGenerator,
    private val semanticAnalyzer: SemanticAnalyzer,
    private val supportedStructures: List<Structure>
) {
    fun parse(tokenIterator: Iterator<Token>): ParserResult {
        val result = ParserResult()

        val statements = getStatements(tokenIterator, result)

        if (!result.hasErrors()) {
            result.programNode = ProgramNode(
                type = "ProgramNode",
                location = Location(1, 1),
                statements = statements
            )
        }

        return result
    }

    private fun getStatements(tokenIterator: Iterator<Token> , result: ParserResult): List<ASTNode> {
        val buffer = ArrayList<Token>()
        val statements = ArrayList<ASTNode>()

        while (tokenIterator.hasNext()) {
            val token = tokenIterator.next()
            buffer.add(token)
            if (checkIfStructureToken(token.type)) {
                handleStructure(token.type, tokenIterator, buffer, statements, result)
            } else if (token.type == "SemicolonToken") {
                handleStatement(buffer, statements, result)
            }
        }

        // If the buffer is not empty, there was a missing semicolon
        if (buffer.isNotEmpty()) {
            result.addError("Unexpected end of input. Missing semicolon or brace at the end of the file.")
        }

        return statements
    }

    // Simply get all the tokens concerning that structure and handle them
    private fun handleStructure(type: String, tokenIterator: Iterator<Token>, buffer: ArrayList<Token>, statements: ArrayList<ASTNode>, result: ParserResult) {
        supportedStructures.forEach {
            if (it.type == type) {
                it.getTokens(tokenIterator, buffer)
            }
        }
        handleStatement(buffer, statements, result)
    }

    // If the token is a semicolon, generate an AST node from the buffer and analyze it.
    private fun handleStatement(buffer: ArrayList<Token>, statements: ArrayList<ASTNode>, result: ParserResult) {
        try {
            val node: ASTNode = astGenerator.generate(buffer, this)
            try {
                semanticAnalyzer.analyze(node)
                statements.add(node)
            } catch (e: Exception) {
                result.addError("Semantic error: ${e.message}")
            }
        } catch (e: Exception) {
            result.addError("Syntactic error: ${e.message}")
        }
        // val size = buffer.size
        buffer.clear()
        // return size
    }

    private fun checkIfStructureToken(string: String): Boolean {
        val structureTokens = supportedStructures.map { it.type }
        return structureTokens.contains(string)
    }
}
