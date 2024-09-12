package org

import kotlinx.serialization.json.JsonObject
import org.astnode.ASTNode
import org.astnode.astnodevisitor.VisitorResult
import org.checkvisitors.CheckVisitors
import org.iterator.PrintScriptIterator

class Linter(
    private val version: String,
    private val nodeIterator: PrintScriptIterator<ASTNode>
) {
    private val warnings = mutableListOf<String>()

    fun lint(jsonFile: JsonObject): LinterResult {
        val checkVisitors: List<CheckVisitors> = LinterVisitorsFactory()
            .createLinterVisitorsFromJson(version, jsonFile)

        while (nodeIterator.hasNext()) {
            val node = nodeIterator.next()!!
            checkVisitors.forEach { visitor ->
                visitor.visit(node) // Primero visito todos los nodos
            }
        }

        checkVisitors.forEach { visitor -> // Luego chequeo los warnings y los agrego
            val result: VisitorResult = visitor.checkWarnings()

            if (result is VisitorResult.ListResult && result.value.isNotEmpty()) {
                warnings.addAll(result.value)
            }
        }
        return LinterResult(warnings) // devuelvo el reporte con todos los warnings.
    }
}
