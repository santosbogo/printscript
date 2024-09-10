package org

import TestReader
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream
import java.io.StringReader

class LexerTesterV11 {
    private fun collectTokensFromLexer(lexer: Lexer): LexerResult {
        val tokens = mutableListOf<Token>()
        val errors = mutableListOf<String>()

        // Collect all tokens from the lexer
        while (lexer.hasNext()) {
            try {
                tokens.add(lexer.next())
            } catch (e: Exception) {
                errors.add(e.message ?: "Unknown error")
            }
        }

        // Return a LexerResult object based on the collected tokens and errors
        val result = LexerResult()
        tokens.forEach { result.addToken(it) }
        errors.forEach { result.addError(it) }
        return result
    }

    @Test
    fun testFile() {
        val file = File("src/test/resources/examples-v11/missingsemicolon.txt")
        val reader = TestReader()

        val (code, solution, shouldSucceed) = reader.readTokens(file.path)
        val lexerReader = StringReader(code)
        val lexer = Lexer(LexiconFactory().createLexiconV11(), lexerReader)
        val lexerResult = collectTokensFromLexer(lexer)


        if (shouldSucceed && lexerResult.hasErrors()) {
            assert(false) { "Unexpected error in file ${file.name}: ${lexerResult.errors.first()}" }
            return
        }

        if (!shouldSucceed && !lexerResult.hasErrors()) {
            assert(false) { "Expected an error but test passed for file ${file.name}" }
            return
        }

        if (!shouldSucceed && lexerResult.hasErrors()) {
            return
        }

        checkTokenMatch(lexerResult.tokens, solution, file.name)
    }

    @Test
    fun testMultipleFiles() {
        val reader = TestReader()
        val examplesDir = File("src/test/resources/examples-v11")

        examplesDir.listFiles { file -> file.isFile && file.extension == "txt" }?.forEach { file ->
            val (code, solution, shouldSucceed) = reader.readTokens(file.path)
            val lexerReader = StringReader(code)
            val lexer = Lexer(LexiconFactory().createLexiconV11(), lexerReader)
            val lexerResult = collectTokensFromLexer(lexer)


            if (shouldSucceed && lexerResult.hasErrors()) {
                assert(false) { "Unexpected error in file ${file.name}: ${lexerResult.errors.first()}" }
                return@forEach
            }

            if (!shouldSucceed && !lexerResult.hasErrors()) {
                assert(false) { "Expected an error but test passed for file ${file.name}" }
                return@forEach
            }

            if (!shouldSucceed && lexerResult.hasErrors()) {
                return@forEach
            }

            checkTokenMatch(lexerResult.tokens, solution, file.name)
        }
    }

    private fun checkTokenMatch(tokens: List<Token>, solution: List<String>, fileName: String) {
        tokens.forEachIndexed { index, token ->
            assert(token.type == solution[index]) {
                "Mismatch in file $fileName at ${token.location}: " +
                    "expected ${solution[index]}, found ${token.type}"
            }
        }
    }
}
