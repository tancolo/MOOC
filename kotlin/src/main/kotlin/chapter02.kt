import test.parseInt

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

    // test array
//    testArray()

    // test If Expression
//    testIfExpression02(1, 3)

    // test when expression
//    testWhenExpression02(3)

    // test Nothing
    testBackAndReturn("john.tan")
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


fun testArray() {
    val asc = Array(5) { i -> (i * i).toString() }
    asc.forEach { println(it) }
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

    val text = """
    |Tell me and I forget.
    >Teach me and I remember.
    |Involve me and I learn.
    >(Benjamin Franklin)
    """.trimMargin()

    println(text)
}


fun testIfExpression01(a: Int, b: Int) = if (a > b) a else b

fun testIfExpression02(a: Int, b: Int) {
    val max = if (a > b) {
        println("Choose a")
        a
    } else {
        println("Choose b")
        b
    }
}


fun testWhenExpression01(x: Int) = when (x) {
    1 -> print("x == 1")
    2, 3, 4 -> print("x == 2")

    else -> println("x is neigher 1 nor 2")
}

fun testWhenExpression02(x: Int) = when (x) {
    parseInt("s") -> print("s encodes x")
    parseInt("3") -> print("s encodes x")
    else -> print("s does not encode x")
}

fun testBackAndReturn(name: String?) {
    val s = name ?: throw IllegalAccessException("Name required")
    println("name is $s")
}


