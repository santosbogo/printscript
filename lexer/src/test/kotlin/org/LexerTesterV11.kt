package org

import TestReader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.io.StringReader

class LexerTesterV11 {

    @Test
    fun testFile() {
        val file = File("src/test/resources/examples-v11/missingsemicolon.txt")
        val reader = TestReader()

        val (code, solution, shouldSucceed) = reader.readTokens(file.path)
        val lexerReader = StringReader(code)
        val lexer = Lexer(LexiconFactory().createLexiconV11(), lexerReader)
        val lexerResult = lexer.collectAllTokens()

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
            val lexerResult = lexer.collectAllTokens()

            if (shouldSucceed && lexerResult.hasErrors()) {
                assert(false) {
                    "Unexpected error in file ${file.name}: ${lexerResult.errors.first()}"
                }
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

    @Test
    fun testTokenPositions() {
        val input = """
            let a = "hello";
        """.trimIndent()

        // Lista de posiciones esperadas para cada token en la entrada
        val expectedPositions = listOf(
            Location(1, 1), // "let"
            Location(1, 5), // "a"
            Location(1, 7), // "="
            Location(1, 9), // "10"
            Location(1, 16), // ";"
        )

        // Crear el lexer
        val lexerReader = StringReader(input)
        val lexer = Lexer(LexiconFactory().createLexiconV10(), lexerReader)
        val lexerResult = lexer.collectAllTokens()

        // Obtener los tokens del lexer
        val tokens = lexerResult.tokens

        // Comparar solo la posición de cada token con la posición esperada
        expectedPositions.forEachIndexed { index, expectedLocation ->
            val token = tokens[index]
            // Solo comparar la posición (línea y columna)
            assertEquals(
                expectedLocation.toString(),
                token.location.toString(),
                "Token location mismatch at index $index"
            )
        }
    }
}
