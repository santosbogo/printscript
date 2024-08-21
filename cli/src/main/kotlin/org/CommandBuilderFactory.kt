package org

import org.commandbuilder.AnalyzingCommandBuilder
import org.commandbuilder.CommandBuilder
import org.commandbuilder.ExecuteCommandBuilder
import org.commandbuilder.FormattingCommandBuilder
import org.commandbuilder.ValidationCommandBuilder

class CommandBuilderFactory {
    fun createCommandBuilders(): Map<String, CommandBuilder> {
        return mapOf(
            "execute" to ExecuteCommandBuilder(Lexer(), Parser(), Interpreter()),
            "validate" to ValidationCommandBuilder(Lexer(), Parser()),
            "format" to FormattingCommandBuilder(Lexer(), Parser()),
            "analyze" to AnalyzingCommandBuilder(Lexer(), Parser())
        )
    }
}
