package org

import org.inputers.NoInputProvider
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.printers.TestPrinter
import java.io.StringReader

class InterpreterTesterV10 {

    @Test
    fun testInterpretAssignment() {
        val str = "let x: number = 42; println(x)"
        val lexer = Lexer(LexiconFactory().createLexiconV10(), StringReader(str))
        val parser = ParserFactory.createParserV10(lexer)
        val printer = TestPrinter()
        val interpreter = InterpreterFactory.createTestInterpreterV10(printer, NoInputProvider(), parser)

        val exception = Assertions.assertThrows(Exception::class.java) {
            interpreter.interpret()
        }

        assertEquals(
            "Unexpected end of input. Missing semicolon or brace at the end of the file.",
            exception.message
        )
    }

    private fun interpretAndCaptureOutputV10(input: String): String {
        val lexer = Lexer(LexiconFactory().createLexiconV10(), StringReader(input))
        val parser = ParserFactory.createParserV10(lexer)
        val printer = TestPrinter()
        val interpreter = InterpreterFactory.createTestInterpreterV10(printer, NoInputProvider(), parser)

        val interpreterResult = interpreter.interpret()

        return printer.printsList.joinToString(separator = "")
    }

    @Test
    fun testPrintlnWithNumber() {
        val input = "println(4);"
        val output = interpretAndCaptureOutputV10(input)
        assertEquals("4", output)
    }

    @Test
    fun testPrintlnWithString() {
        val input = "let a: string = 'hola'; println(a);"
        val output = interpretAndCaptureOutputV10(input)
        assertEquals("hola", output)
    }

    @Test
    fun testPrintlnWithStringAndQuotes() {
        val input = "println(\"hola\");"
        val output = interpretAndCaptureOutputV10(input)
        assertEquals("hola", output)
    }

    @Test
    fun testPrintlnWithVariableAssignment() {
        val input = "let b: number = 10; b = 5; println(b);"
        val output = interpretAndCaptureOutputV10(input)
        assertEquals("5", output)
    }

    @Test
    fun testPrintlnWithStringConcatenation() {
        val input = "let a: string = 'hola'; let b: number = 5; println(a + b);"
        val output = interpretAndCaptureOutputV10(input)
        assertEquals("hola5", output)
    }

    @Test
    fun testPrintlnWithAddition() {
        val input = "println(1 + 4);"
        val output = interpretAndCaptureOutputV10(input)
        assertEquals("5", output)
    }

    @Test
    fun testPrintlnWithSubtraction() {
        val input = "println(5 - 1);"
        val output = interpretAndCaptureOutputV10(input)
        assertEquals("4", output)
    }

    @Test
    fun testPrintlnWithMultiplication() {
        val input = "println(5 * 2);"
        val output = interpretAndCaptureOutputV10(input)
        assertEquals("10", output)
    }

    @Test
    fun testPrintlnWithDivision() {
        val input = "println(10 / 2);"
        val output = interpretAndCaptureOutputV10(input)
        assertEquals("5", output)
    }
}
