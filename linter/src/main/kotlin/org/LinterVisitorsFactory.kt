package org

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.checkvisitors.CheckVisitors
import org.checkvisitors.NamingFormatCheckVisitor
import org.checkvisitors.PrintUseCheckVisitor
import org.checkvisitors.ReadInputCheckVisitor
import org.checkvisitors.UnusedVariableCheckVisitor
import org.expressionfactory.PatternFactory

class LinterVisitorsFactory {
    fun createLinterVisitorsDefault(version: String): List<CheckVisitors> {
        return when (version) {
            "1.0" -> createAvailableLinterVisitorsV10()
            "1.1" -> createAvailableLinterVisitorsV11()
            else -> throw IllegalArgumentException("Unknown version: $version")
        }
    }

    fun createLinterVisitorsFromJson(version: String, jsonFile: JsonObject): List<CheckVisitors> {
        return when (version) {
            "1.0" -> createLinterVisitorsV10(jsonFile)
            "1.1" -> createLinterVisitorsV11(jsonFile)
            else -> throw IllegalArgumentException("Unknown version: $version")
        }
    }

    private fun createAvailableLinterVisitorsV10(): List<CheckVisitors> {
        val visitors = mutableListOf<CheckVisitors>()
        visitors.add(UnusedVariableCheckVisitor())
        visitors.add(
            NamingFormatCheckVisitor(
                "camelCase",
                PatternFactory.getNamingFormatPattern("camelCase")
            )
        )
        visitors.add(PrintUseCheckVisitor(false))
        return visitors
    }

    private fun createAvailableLinterVisitorsV11(): List<CheckVisitors> {
        val visitors = mutableListOf<CheckVisitors>()
        visitors.add(UnusedVariableCheckVisitor())
        visitors.add(
            NamingFormatCheckVisitor(
                "camelCase",
                PatternFactory.getNamingFormatPattern("camelCase")
            )
        )
        visitors.add(PrintUseCheckVisitor(false))
        visitors.add(ReadInputCheckVisitor(false))
        return visitors
    }

    private fun createLinterVisitorsV10(jsonFile: JsonObject): List<CheckVisitors> {
        val visitors = mutableListOf<CheckVisitors>()

        // pass jsonObject to string
        for ((key, value) in jsonFile) {
            when (key) {
                "UnusedVariableCheck" -> {
                    visitors.add(UnusedVariableCheckVisitor())
                }

                "NamingFormatCheck" -> {
                    // agarro que naming pattern se quiere usar
                    val namingPatternName = value
                        .jsonObject["namingPatternName"]?.jsonPrimitive?.content ?: ""
                    val pattern = PatternFactory.getNamingFormatPattern(namingPatternName)
                    visitors.add(NamingFormatCheckVisitor(namingPatternName, pattern))
                }

                "PrintUseCheck" -> {
                    // agarro si esta habilitado o no el check. seteo default a falso.
                    val printlnCheckEnabled = value
                        .jsonObject["printlnCheckEnabled"]?.jsonPrimitive?.boolean == true
                    visitors.add(PrintUseCheckVisitor(printlnCheckEnabled))
                }

                else -> throw IllegalArgumentException("Unknown check: $key")
            }
        }

        return visitors
    }

    private fun createLinterVisitorsV11(jsonFile: JsonObject): List<CheckVisitors> {
        val visitors = mutableListOf<CheckVisitors>()

        // pass jsonObject to string
        for ((key, value) in jsonFile) {
            when (key) {
                "UnusedVariableCheck" -> {
                    visitors.add(UnusedVariableCheckVisitor())
                }

                "NamingFormatCheck" -> {
                    // agarro que naming pattern se quiere usar
                    val namingPatternName = value
                        .jsonObject["namingPatternName"]?.jsonPrimitive?.content ?: ""
                    val pattern = PatternFactory.getNamingFormatPattern(namingPatternName)
                    visitors.add(NamingFormatCheckVisitor(namingPatternName, pattern))
                }

                "PrintUseCheck" -> {
                    // agarro si esta habilitado o no el check. seteo default a falso.
                    val printlnCheckEnabled = value
                        .jsonObject["printlnCheckEnabled"]?.jsonPrimitive?.boolean == true
                    visitors.add(PrintUseCheckVisitor(printlnCheckEnabled))
                }

                "ReadInputCheck" -> {
                    val readInputCheckEnabled = value
                        .jsonObject["readInputCheckEnabled"]?.jsonPrimitive?.boolean == true
                    visitors.add(ReadInputCheckVisitor(readInputCheckEnabled))
                }

                else -> throw IllegalArgumentException("Unknown check: $key")
            }
        }

        return visitors
    }
}
