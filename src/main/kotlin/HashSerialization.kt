import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.io.PrintWriter

class HashSerialization {
    @Throws(IOException::class)
    fun saveToFile(filename: String?, hash: IHash, objectBuilder: ObjectBuilder) {
        try {
            PrintWriter(filename).use { writer ->
                writer.write("<hashMap>\n")
                hash.forEach(object : ActionStarter {
                    override fun toDo(value: Any?) {
                        writer.println(objectBuilder.toString(value))
                    }
                })
                writer.write("</hashMap>")
                writer.close()
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    fun loadFromFile(filename: String?, hash: IHash): IHash {
        try {
            BufferedReader(FileReader(filename)).use { br ->
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    println(line)
                }
                return hash
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}