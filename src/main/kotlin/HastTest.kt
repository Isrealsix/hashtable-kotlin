import junit.framework.Assert.assertEquals
import org.junit.Test
import kotlin.test.assertNotNull

class HashTest {
    var hash: Hash = Hash(5)
    var factory = ObjectFactory().apply {
        objectRecorder(StringObjectBuilder())
        objectRecorder(IntObjectBuilder())
    }
    var vector2D = Vector2D()
    var hashSerialization = HashSerialization()
    @Test
    fun testAddString() {
        val start = System.currentTimeMillis()
        val stringObjectBuilder = factory.getBuilderByName("String")
        for (i in 0 until 100000) {
            hash.put(stringObjectBuilder, StringObjectBuilder().create())
//            hash.put(4, StringObjectBuilder().create())
//            hash.put(6, StringObjectBuilder().create())
//            hash.put(100, StringObjectBuilder().create())
//            hash.put(100, StringObjectBuilder().create())
        }

        println(hash)
        assertEquals(true, hash != null)
        val finish = System.currentTimeMillis() - start
        println("Time added: $finish")
    }

    @Test
    fun testAddVector() {
        val start = System.currentTimeMillis()
        val stringObjectBuilder = factory.getBuilderByName("Vector2D")
        for (i in 0 until 100) {
            hash.put(stringObjectBuilder?.create(), StringObjectBuilder().create())
        }

        assertEquals(true, hash != null)
        val finish = System.currentTimeMillis() - start
        println("Time added: $finish")
        println(hash)
    }

    @Test
    fun testToString() {
        val start = System.currentTimeMillis()
        assertNotNull(hash.toString())
        assertEquals("0 null\n1 null\n2 null\n3 null\n4 null\n", hash.toString())
        val finish = System.currentTimeMillis() - start
        println("Time to string: $finish")
    }

    @Test
    fun testFailedToString() {
        val start = System.currentTimeMillis()
        assertEquals(false, hash.toString() == null)
        val finish = System.currentTimeMillis() - start
        println("Time failed: $finish")
    }

    @Test
    fun testGet() {
        val start = System.currentTimeMillis()
        val value = vector2D.create()
        val key = StringObjectBuilder().create()
        hash.put(value, key)
        val keyD = value.hashCode();
        println(hash[keyD])
        assertEquals(value, hash[keyD])
        val finish = System.currentTimeMillis() - start
        println("Time to get: $finish")
    }

    @Test
    fun testAddSame() {
        val start = System.currentTimeMillis()
        val stringObjectBuilder = factory.getBuilderByName("String")
        for (i in 0 until 10) {
            hash.put(5, StringObjectBuilder().create())
        }

        println(hash)
        assertEquals(true, hash != null)
        val finish = System.currentTimeMillis() - start
        println("Time added: $finish")
    }

    @Test
    fun testAddUnordered() {
        val start = System.currentTimeMillis()
        val stringObjectBuilder = factory.getBuilderByName("String")
        for (i in 0 until 10) {
            hash.put(5, StringObjectBuilder().create())
            hash.put(5, StringObjectBuilder().create())
            hash.put(5, StringObjectBuilder().create())
            hash.put(5, StringObjectBuilder().create())
            hash.put(5, StringObjectBuilder().create())
        }

        println(hash)
        assertEquals(true, hash != null)
        val finish = System.currentTimeMillis() - start
        println("Time added: $finish")
    }

    @Test
    fun testAddOrdered() {
        val start = System.currentTimeMillis()
        val stringObjectBuilder = factory.getBuilderByName("String")
        for (i in 0 until 10) {
            hash.put(1, StringObjectBuilder().create())
            hash.put(2, StringObjectBuilder().create())
            hash.put(3, StringObjectBuilder().create())
            hash.put(4, StringObjectBuilder().create())
            hash.put(5, StringObjectBuilder().create())
        }

        println(hash)
        assertEquals(true, hash != null)
        val finish = System.currentTimeMillis() - start
        println("Time added: $finish")
    }

    @Test
    fun testAddDecending() {
        val start = System.currentTimeMillis()
        val stringObjectBuilder = factory.getBuilderByName("String")
        for (i in 0 until 10) {
            hash.put(5, StringObjectBuilder().create())
            hash.put(4, StringObjectBuilder().create())
            hash.put(3, StringObjectBuilder().create())
            hash.put(2, StringObjectBuilder().create())
            hash.put(1, StringObjectBuilder().create())
        }

        println(hash)
        assertEquals(true, hash != null)
        val finish = System.currentTimeMillis() - start
        println("Time added: $finish")
    }

    @Test
    fun testGetAnotherOne() {
        val start = System.currentTimeMillis()
        val value = vector2D.create()
        val key = StringObjectBuilder().create()
        hash.put(value, key)
        val keyD = value.hashCode();
        println(hash[keyD])
        assertEquals(value, hash[keyD])
        val finish = System.currentTimeMillis() - start
        println("Time to get another one: $finish")
    }

//    @Test
//    fun testGetFailed() {
//        val start = System.currentTimeMillis()
//        val value = vector2D.create()
//        val key = StringObjectBuilder().create()
//        hash.put(value, key)
//        val keyD = "Failed";
//        println(value)
//        assertEquals(value, value == null)
//        val finish = System.currentTimeMillis() - start
//        println("Time to get another one: $finish")
//    }

    @Test
    fun testRemove() {
        val start = System.currentTimeMillis()
        var value = vector2D.create()
        var key = StringObjectBuilder().create()
        hash.put(value, key)
        var keyD = value.hashCode();

        hash.put(vector2D.create(), StringObjectBuilder().create())
        assertEquals(value, hash.remove(keyD)!!.value)
        val finish = System.currentTimeMillis() - start
        println("Time to remove: $finish")
    }

    @Test
    fun testResize() {
        val start = System.currentTimeMillis()
        var value = vector2D.create()
        var key = StringObjectBuilder().create()
        hash.put(value, key)
        var keyD = value.hashCode();

        hash.put(vector2D.create(), StringObjectBuilder().create())
        hash = hash.resizeHash()

        assertEquals(value, hash.remove(keyD)!!.value)
        val finish = System.currentTimeMillis() - start
        println("Time to resize: $finish")
    }

//    @Test
//    fun testHashToString() {
//        val start = System.currentTimeMillis()
//        val hashTest = HashTest()
//
//        hashTest.put();
////        assertEquals(true, hashTest.put()!!)
//
//        println(hashTest.toString())
//
//        hashTest.testToString()
//    }
}