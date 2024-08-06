import java.io.File

class TestReader {

    fun readTokens(path: String): Triple<String, List<String>, Boolean> {
        val content = File(path).readText()
        val parts = content.split("#####")
        val codePart = parts[0].trim()
        val solutionPart = parts[1].trim().split("\n")
        val shouldSucceed = parts[2].trim().toBoolean()
        return Triple(codePart, solutionPart, shouldSucceed)
    }
}