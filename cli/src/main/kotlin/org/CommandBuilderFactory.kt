package org

import org.commandbuilder.ExecuteCommandBuilder
import org.commandbuilder.AnalyzingCommandBuilder
import org.commandbuilder.FormattingCommandBuilder
import org.commandbuilder.ValidationCommandBuilder
import org.commandbuilder.CommandBuilder

class CommandBuilderFactory {
    fun createCommandBuilders(): Map<String, CommandBuilder> {
        return mapOf(
            "execute" to ExecuteCommandBuilder(),
            "validate" to ValidationCommandBuilder(),
            "format" to FormattingCommandBuilder(),
            "analyze" to AnalyzingCommandBuilder()
        )
    }
}