package org

import TestReader
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.astnode.ProgramNode
import org.junit.jupiter.api.Test
import java.io.File

class LinterTesterV10 {
    @Test
    fun testSingleWarning() {
        val file = File("src/test/resources/examples-v10/namingFormatExample2.txt")

        // me devuelve el codigo que entra, la warning q tiene q devolver, y si debería funcionar.
        val reader = TestReader()
        val (code, expectedWarning, shouldSucceed) = reader.readTokens(file.path)

        // meto el codigo en el lexer, obtengo tokens
        val lexer = LexerFactory.createLexerV10()
        val lexerResult = lexer.tokenize(code)

        // meto tokens en el parser, obtengo los nodos.
        val parser = ParserFactory.createParserV10()
        val parserResult = parser.parse(lexerResult.tokens)
        val programNode = parserResult.programNode!!

        val linter = LinterFactory().createFormatterV10()

        val jsonContent = File("src/test/kotlin/org/jsons/jsonV10.json").readText()
        val jsonObject = Json.parseToJsonElement(jsonContent).jsonObject

        // compare the linter output with the expectedWarning.
        compareResults(linter, code, programNode, expectedWarning, shouldSucceed, jsonObject)
    }

    @Test
    fun testMultipleWarnings() {
        val dir = File("src/test/resources/examples-v10")

        // declaro todas las clases q voy a usar.
        val reader = TestReader()
        val lexer = LexerFactory.createLexerV10()

        // para cada archivo de texto, corro el test. Me permite correr varios tests automaticos.
        dir.listFiles {
            file ->
            file.isFile && file.extension == "txt" // debe ser un txt y un file.
        }?.forEach {
            file ->
            val (code, expectedWarnings, shouldSucceed) = reader.readTokens(file.path)
            val lexerResult = lexer.tokenize(code)

            val parser = ParserFactory.createParserV10()
            val parserResult = parser.parse(lexerResult.tokens)
            val programNode = parserResult.programNode!!

            val linter = LinterFactory().createFormatterV10()

            val jsonContent = File("src/test/kotlin/org/jsons/jsonV10.json").readText()
            val jsonObject = Json.parseToJsonElement(jsonContent).jsonObject

            compareResults(linter, code, programNode, expectedWarnings, shouldSucceed, jsonObject)
        }
    }

    private fun compareResults(
        linter: Linter,
        code: String,
        programNode: ProgramNode,
        expectedWarnings: List<String>,
        shouldSucceed: Boolean,
        jsonFile: JsonObject
    ) {
        val reportList = linter.lint(programNode, jsonFile).getList()
        if (!shouldSucceed) {
            assert(false) { "Expected an error but test passed for code $code" }
        }

        // voy chequeando q los warnings q me devuelve el linter sean los q espero.
        expectedWarnings.forEachIndexed() {
            index, expectedWarning ->
            assert(reportList[index] == expectedWarning) {
                "Mismatch in code \"$code\": expected \"$expectedWarning\", found \"${reportList[index]}\""
            }
        }
    }
}
