package org

class Report(private var lista: List<String> = emptyList()) {
    // reporte con una lista de String (warnings, errors, etc)

    fun addToList(element: String) {
        lista = lista + element
    }

    fun getList(): List<String> {
        return lista
    }
}
