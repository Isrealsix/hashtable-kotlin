import java.lang.Math.abs
import java.util.*


class Hash(var size: Int) : IHash, Cloneable {
    var totalValue = 0
    var maxValue = 0
    inner class Entry constructor(val key: Any?, var value: Any?) {
        var next: Entry? = null

        override fun toString(): String {
            var tmp: Entry? = this
            val sb = StringBuilder()
            while (tmp != null) {
                sb.append(tmp.key.toString() + " -> " + tmp.value + "; ")
                tmp = tmp.next
            }
            return sb.toString()
        }
    }

    private val array: Array<Entry?>

    init {
        array = arrayOfNulls(size)
    }

    override fun put(value: Any?, create: String?) {
        val originalHash = value.hashCode()
        val hash = if (value.hashCode() > 0) {
            value.hashCode() % size
        } else {
            - value.hashCode() % size
        }
        var e = array[hash]
        if (e == null) {
            array[hash] = Entry(originalHash, value)
        } else {
            while (e!!.next != null) {
                if (e.key == originalHash) {
                    e.value = value
                    return
                }
                e = e.next
            }
            if (e.key == originalHash) {
                e.value = value
                return
            }
            e.next = Entry(originalHash, value)
        }
    }

//    override fun get(key: Any): Any? {
//        val hash = key.hashCode() % size
//        var e = array[hash] ?: return null
//        while (e != null) {
//            if (e.key == key) {
//                return e.value
//            }
//            e = e.next!!
//        }
//        return null
//    }

    override fun get(key: Any): Any? {
        val hash = (key.hashCode() and 0x7FFFFFFF) % size
        val bucket = array[hash]
        var currentEntry = bucket

        while (currentEntry != null) {
            val currentKey = currentEntry.key
            val currentHash = currentKey.toString().toInt()
            val absKey = abs(currentHash)

            if (absKey == key) {
                return currentEntry.value
            }

            currentEntry = currentEntry.next
        }

        return null
    }

//    fun remove(key: Any): Entry? {
//        val hash = key.hashCode() % size
//        var e = array[hash] ?: return null
//        if (e.key == key) {
//            array[hash] = e.next
//            e.next = null
//            return e
//        }
//        var prev = e
//        e = e.next!!
//        while (e != null) {
//            if (e.key == key) {
//                prev = e
//                e = e.next!!
//                prev.next = e.next
//                e.next = null
//                return e
//            }
//        }
//        return null
//    }

    fun remove(key: Any): Entry? {
        val hash = (key.hashCode() and 0x7FFFFFFF) % size
        var e = array[hash]
        val rawKey = e?.key
        if (rawKey == null) return null
        val absKey = abs(rawKey.toString().toInt())

        if (absKey == key) {
            if (e != null) {
                array[hash] = e.next
            }
            if (e != null) {
                e.next = null
            }
            return e
        }
        var prev = e
        if (e != null) {
            e = e.next!!
        }
        while (e != null) {
            if (absKey == key) {
                prev = e
                e = e.next!!
                prev.next = e.next
                e.next = null
                return e
            }
        }
        return null
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (i in 0 until size) {
            if (array[i] != null) {
                sb.append(
                    """	$i ${array[i]}
"""
                )
            } else {
                sb.append("$i null\n")
            }
        }
        return sb.toString()
    }

    override fun forEach(`as`: ActionStarter) {
        var e = array[0]
        for (i in 0 until size) {
            e = array[i]
            `as`.toDo(e)
        }
    }

    fun sizeRecorder() {
        var middleValue = 0
        var min = Int.MAX_VALUE
        var max = 0
        array.forEach { entry ->
            var e = entry
            var currValue = 0
            while(e != null){
                currValue++
                e = e.next
            }
            if (min > currValue) min = currValue
            if (max < currValue) max = currValue
            middleValue += currValue
        }
        middleValue = middleValue / size
        totalValue = max - min - middleValue
        maxValue = max
        println("--------------------------------------------------------------------------------")
        println("Размер: $size")
        println("Минимальное значение: $min")
        println("Максимальное значение: $max")
        println("Среднее значение: $middleValue")
        println("Значение, которое мы берём для увеличения хэш-таблицы((макс - мин) - ср.знач): $totalValue")
        println("Для просмотра, по окончании итераций хэш-таблицы, нажмите Hash")
        println("--------------------------------------------------------------------------------")
    }

    @Throws(CloneNotSupportedException::class)
    public override fun clone(): Hash {
        return super.clone() as Hash
    }

    fun insert(hash: Hash): Hash {
        for (i in 0 until size) {
            var e = array[i]
            while (e != null) {
                hash.put(e.value, StringObjectBuilder().create())
                e = e.next
            }
        }
        return hash
    }

    fun resizeHash(): Hash {
        var newHash = this
        newHash.sizeRecorder()
        if(newHash.maxValue > newHash.totalValue) {
            val hash1 = clone()
            while (newHash.maxValue > newHash.totalValue) {
                newHash = Hash(newHash.size * 2)
                newHash = hash1.insert(newHash)
                    /*for (i in 0 until number) {
                    hash = hash1.insert(hash)
                }*/
                newHash.sizeRecorder()
            }
        }
        return newHash
    }
}