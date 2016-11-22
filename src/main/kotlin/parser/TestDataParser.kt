package parser

import java.io.File
import java.io.IOException
import java.util.*

class TestDataParser {

    fun readGrammar(): MutableList<String> {
        return readFileFromResourcesDirectory("grammar")
    }

    fun readLexicon(): MutableList<String> {
        return readFileFromResourcesDirectory("lexicon")
    }

    private fun readFileFromResourcesDirectory(fileName: String): MutableList<String> {
        val productions = LinkedList<String>()
        val classLoader = javaClass.classLoader
        try {
            val file = File(classLoader.getResource(fileName)!!.file)

            try {
                Scanner(file).use { scanner ->
                    while (scanner.hasNextLine()) {
                        val line = scanner.nextLine()
                        if (line == "") break
                        productions.add(line.trim() + "\n")
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

        return productions
    }
}