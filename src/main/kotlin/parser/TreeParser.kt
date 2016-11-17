package parser

import model.Node
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class TreeParser {

    private val parser: RecursiveLineParser
    private val tokenizer: Tokenizer

    init {
        parser = RecursiveLineParser()
        tokenizer = Tokenizer()
    }

    fun parseTreeFile(fileName: String): MutableList<Node> {
        return readFileFromCommandLineInSameFolder(fileName) ?: readFileFromResourcesDirectory(fileName)
    }

    private fun readFileFromCommandLineInSameFolder(fileName: String): MutableList<Node>? {
        val trees = LinkedList<Node>()
        try {
            Files.lines(Paths.get(fileName))
                    .map { s -> formatInputTree(s) }
                    .map { s -> tokenizer.scan(s) }
                    .map { s -> parser.parseLineToTree(s) }
                    .forEach { s -> trees.add(s) }
            return trees
        } catch (e: IOException) {
            System.err.println("File $fileName not found in this folder")
            return null
        }

    }

    private fun readFileFromResourcesDirectory(fileName: String): MutableList<Node> {
        val trees = LinkedList<Node>()
        val classLoader = javaClass.classLoader
        try {
            val file = File(classLoader.getResource(fileName)!!.file)

            try {
                Scanner(file).use { scanner ->
                    while (scanner.hasNextLine()) {
                        var line = scanner.nextLine()
                        if (line == "") break
                        line = formatInputTree(line)
                        trees.add(parser.parseLineToTree(tokenizer.scan(line)))
                    }
                    scanner.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        } catch (e: NullPointerException) {
            System.err.println("Datei $fileName wurde nicht im Resources-Verzeichnis gefunden")
            System.exit(1)
        }

        return trees
    }

    fun formatInputTree(input: String): String {
        return input.substring(2, input.length - 2)
    }
}
