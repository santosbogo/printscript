package org

sealed class RunnerResult {
    data class Execute(val printList: List<String>, val errorsList: List<String>) : RunnerResult()
    data class Analyze(val warningsList: List<String>, val errorsList: List<String>) : RunnerResult()
    data class Format(val formattedCode: String, val errorsList: List<String>) : RunnerResult()
    data class Validate(val errorsList: List<String>) : RunnerResult()
}
