package org

import org.commandbuilder.AnalyzingCommandBuilder
import org.commandbuilder.CommandBuilder
import org.commandbuilder.ExecuteCommandBuilder
import org.commandbuilder.FormattingCommandBuilder
import org.commandbuilder.ValidationCommandBuilder

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
