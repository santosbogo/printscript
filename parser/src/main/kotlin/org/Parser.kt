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
    fun parse(tokens: List<Token>): ParserResult {
        val result = ParserResult()

        val statements = getStatements(tokens, result)

        if (!result.hasErrors()) {
            result.programNode = ProgramNode(
                type = "ProgramNode",
                location = Location(1, 1),
                statements = statements
            )
        }

        return result
    }

    private fun getStatements(tokens: List<Token>, result: ParserResult): List<ASTNode> {
        val buffer = ArrayList<Token>()
        val statements = ArrayList<ASTNode>()

        var i = 0 // We use indexes to make jumps when a structure is encountered
        while (i < tokens.size) {
            val token = tokens[i]
            buffer.add(token)
            if (checkIfStructureToken(token.type)) {
                i = handleStructure(token.type, tokens, i, buffer, statements, result)
                i--
            } else if (token.type == "SemicolonToken") {
                handleStatement(buffer, statements, result)
            }
            i++
        }

        // If the buffer is not empty, there was a missing semicolon
        if (buffer.isNotEmpty()) {
            result.addError("Unexpected end of input. Missing semicolon or brace at the end of the file.")
        }

        return statements
    }

    // Simply get all the tokens concerning that structure and handle them
    private fun handleStructure(type: String, tokens: List<Token>, i: Int, buffer: ArrayList<Token>, statements: ArrayList<ASTNode>, result: ParserResult): Int {
        supportedStructures.forEach {
            if (it.type == type) { it.getTokens(tokens, i, buffer) }
        }
        val b = handleStatement(buffer, statements, result)
        return i + b
    }

    // If the token is a semicolon, generate an AST node from the buffer and analyze it.
    private fun handleStatement(buffer: ArrayList<Token>, statements: ArrayList<ASTNode>, result: ParserResult): Int {
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
        val size = getBufferSize(buffer)
        buffer.clear()
        return size
    }

    private fun getBufferSize(tokens: ArrayList<Token>): Int {
        if (hasSeparator(tokens)) {
            return tokens.size - 1
        }
        return tokens.size
    }

    private fun hasSeparator(tokens: ArrayList<Token>): Boolean {
        for (token in tokens) {
            if (token.type == "Separator") {
                return true
            }
        }
        return false
    }

    private fun checkIfStructureToken(string: String): Boolean {
        val structureTokens = supportedStructures.map { it.type }
        return structureTokens.contains(string)
    }
}
