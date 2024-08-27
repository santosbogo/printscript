package org

class LinterResult(private var lista: List<String> = emptyList()) {
    // reporte con una lista de String (warnings, errors, etc)

    fun getList(): List<String> {
        return lista
    }
}
