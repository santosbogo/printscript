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
                lexer = LexerFactory.createLexerV10()
                parser = ParserFactory.createParserV10()
                interpreter = InterpreterFactory.createInterpreterV10()
                linter = LinterFactory().createLinterV10()
                formatter = FormatterFactory().createFormatterV10()
            }

            "1.1" -> {
                lexer = LexerFactory.createLexerV11()
                parser = ParserFactory.createParserV11()
                interpreter = InterpreterFactory.createInterpreterV11()
                linter = LinterFactory().createLinterV11()
                formatter = FormatterFactory().createFormatterV11()
            }

            else -> throw IllegalArgumentException("Unsupported version: $version")
        }
    }

    fun execute(str: String): Triple<List<String>, List<String>, List<String>> {
        val printList = mutableListOf<String>()
        val errorsList = mutableListOf<String>()

        val lexerResult = lexer.tokenize(str)

        if (lexerResult.hasErrors()) {
            lexerResult.errors.forEach { errorsList.add(it) }
            return Triple(printList, errorsList, listOf())
        }

        val parserResult = parser.parse(lexerResult.tokens)

        if (parserResult.programNode == null) {
            parserResult.errors.forEach { errorsList.add(it) }
            return Triple(printList, errorsList, listOf())
        }

        val interpreterResult = interpreter.interpret(parserResult.programNode!!)

        if (interpreterResult.printsList.isNotEmpty()) {
            interpreterResult.printsList.forEach { printList.add(it) }
        }
        return Triple(printList, errorsList, listOf())
    }

    fun analyze(str: String) {
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

    fun validate(str: String) {
        val errorsList = mutableListOf<String>()

        val lexerResult = lexer.tokenize(str)

        if (lexerResult.hasErrors()) {
            lexerResult.errors.forEach { errorsList.add(it) }
        }

        val parserResult = parser.parse(lexerResult.tokens)

        if (parserResult.programNode == null) {
            parserResult.errors.forEach { errorsList.add(it) }
        }
    }

    fun format(str: String) {
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

        val formatterResult = formatter.format(parserResult.programNode!!)
        printList.add(formatterResult.code)
    }
}
