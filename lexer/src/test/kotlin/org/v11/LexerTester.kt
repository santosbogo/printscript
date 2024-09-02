package org.v11

import TestReader
import org.Lexer
import org.LexiconFactory
import org.Token
import org.junit.jupiter.api.Test
import java.io.File

class LexerTester {
    @Test
    fun testFile() {
        val file = File("src/test/resources/examples-v11/ifstatement.txt")
        val lexer = Lexer(LexiconFactory().createLexiconV11())
        val reader = TestReader()

        val (code, solution, shouldSucceed) = reader.readTokens(file.path)
        val lexerResult = lexer.tokenize(code)

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
        val lexer = Lexer(LexiconFactory().createLexiconV11())
        val reader = TestReader()
        val examplesDir = File("src/test/resources/examples-v11")

        examplesDir.listFiles { file -> file.isFile && file.extension == "txt" }?.forEach { file ->
            val (code, solution, shouldSucceed) = reader.readTokens(file.path)
            val lexerResult = lexer.tokenize(code)

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
