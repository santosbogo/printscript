package org.structures

class StructureFactory {
    fun createDefaultStructures(): List<Structure> {
        return listOf(IfElseStructure())
    }
}
