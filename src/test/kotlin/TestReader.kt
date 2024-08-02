import java.io.File

class TestReader {

    fun readTokens(path: String): Pair<String, List<String>> {
        val content = File(path).readText()
        val parts = content.split("#####")
        val codePart = parts[0].trim()
        val solutionPart = parts[1].trim().split("\n")
        return Pair(codePart, solutionPart)
    }
}