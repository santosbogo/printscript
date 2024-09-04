package org

class Runner(version: String) {
    private val lexer: Lexer
    private val parser: Parser
    private val interpreter: Interpreter
    private val linter: Linter
    private val formatter: Formatter

    init {
        when (version) {
            "1.0" -> {
                lexer = Lexer()
                parser = Parser()
                interpreter = Interpreter()
                linter = LinterFactory().createLinterV10()
                formatter = FormatterFactory().createFormatterV10()
            }
            // TODO agregar version 1.1
            else -> throw IllegalArgumentException("Unsupported version: $version")
        }
    }
    fun execute(str: String) {
        val printList = mutableListOf<String>()
        val errorsList = mutableListOf<String>()

        val lexerResult = lexer.tokenize(str)

        if (lexerResult.hasErrors()) {
            lexerResult.errors.forEach { errorsList.add(it) }
        }

        val parserResult = parser.parse(lexerResult.tokens)

        if (parserResult.programNode == null) {
            parserResult.errors.forEach { errorsList.add(it) }
        }

        val interpreterResult = interpreter.interpret(parserResult.programNode!!)

        if (interpreterResult.printsList.isNotEmpty()) {
            interpreterResult.printsList.forEach { printList.add(it) }
        }
    }

    fun analyze(str: String, version: String) {
        val warningsList = mutableListOf<String>()
        val errorsList = mutableListOf<String>()

        val lexerResult = lexer.tokenize(str)

        if (lexerResult.hasErrors()) {
            lexerResult.errors.forEach { errorsList.add(it) }
        }

        val parserResult = parser.parse(lexerResult.tokens)

        if (parserResult.programNode == null) {
            parserResult.errors.forEach { errorsList.add(it) }
        }

        val linterResult = linter.lint(parserResult.programNode!!)
        linterResult.getList().forEach { warningsList.add(it) }
    }
}
