/**
 * Kotlin Basic Type
 */

fun main(args: Array<String>) {
//    testNumberBox()
//    testBitOperator()
//    testStringTemple()

//    var range = "bytes=100-"
//    println("startWith: ${range.startsWith("bytes=")}")
//
//    range = range.substring("bytes=".length)
//    println("range: $range")
//
//    val minus = range.indexOf('-')
//    println("minus: $minus")

    // Support (simple) skipping:
//    var startFrom: Long = 0L
//    var endAt: Long = -1
//    var range = "bytes=0-"
//    try {
//        if (range != null && range!!.startsWith("bytes=")) {
//            range = range!!.substring("bytes=".length)
//            val minus = range!!.indexOf('-')
//            if (minus > 0) {
//                startFrom = java.lang.Long.parseLong(range!!.substring(0, minus))
//                //endAt = java.lang.Long.parseLong(range.substring(minus + 1))
//
//                if (!range.endsWith("-")) {
//                    endAt = java.lang.Long.parseLong(range.substring(minus + 1))
//                }
//            }
//
//            println("AXEL===> startFrom = $startFrom, endAt = $endAt, range = $range")
//        }
//    } catch (e: NumberFormatException) {
//        e.printStackTrace()
//    }


}

// box
fun testNumberBox() {
    val a: Int = 1000
    println(a === a)

    val boxedA: Int? = a
    val anotherBoxedA: Int? = a
    println(boxedA === anotherBoxedA)
    println(boxedA == anotherBoxedA)
}

// bit operator
/**
这是完整的位运算列表（只用于 Int 与 Long）：

shl(bits) – 有符号左移 (Java 的 <<)
shr(bits) – 有符号右移 (Java 的 >>)
ushr(bits) – 无符号右移 (Java 的 >>>)
and(bits) – 位与
or(bits) – 位或
xor(bits) – 位异或
inv() – 位非
 */
fun testBitOperator() {
    val x = (1 shl 2) and 0x000ff000
    println("x: $x")
}


// String template
fun testStringTemple() {
    val test = """
        for (c in "foo")
        print(c)
    """.trimIndent()
    //.trimMargin

    println(test)

    val price = """
        ${'$'}9.99
    """.trimIndent()

    println(price)
}

