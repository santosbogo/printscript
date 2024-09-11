package org

import kotlinx.serialization.json.JsonObject
import org.inputers.InputProvider
import org.printers.Printer
import java.io.Reader

class Runner(version: String, reader: Reader) {
    private val lexer: Lexer
    private val parser: Parser
    private val linter: Linter
    private val formatter: Formatter

    init {
        when (version) {
            "1.0" -> {
                lexer = LexerFactory.createLexerV10(reader)
                parser = ParserFactory.createParserV10(lexer)
                linter = LinterFactory().createLinterV10(parser)
                formatter = Formatter(parser)
            }

            "1.1" -> {
                lexer = LexerFactory.createLexerV11(reader)
                parser = ParserFactory.createParserV11(lexer)
                linter = LinterFactory().createLinterV11(parser)
                formatter = Formatter(parser)
            }

            else -> throw IllegalArgumentException("Unsupported version: $version")
        }
    }

    fun execute(version: String, printer: Printer, inputProvider: InputProvider) {
        val interpreter = InterpreterFactory.createRunnerInterpreter(
            version,
            printer,
            inputProvider,
            parser
        )
        interpreter.interpret()
    }

    fun analyze(jsonFile: JsonObject): RunnerResult.Analyze {
        val warningsList = mutableListOf<String>()
        val errorsList = mutableListOf<String>()

        val linterResult = linter.lint(jsonFile)
        linterResult.getList().forEach { warningsList.add(it) }
        return RunnerResult.Analyze(warningsList, errorsList)
    }

    fun format(json: String, version: String): RunnerResult.Format {
        val errorsList = mutableListOf<String>()

        val rules = RulesFactory().getRules(json, version)
        val formatterResult = formatter.format(rules)
        return RunnerResult.Format(formatterResult.code, errorsList)
    }
}
