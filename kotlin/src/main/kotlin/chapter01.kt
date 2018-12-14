package test


fun main(args: Array<String>) {
//    println("hello world kotlin")
//    println("sum of 1 and 3 is:  ${sum(1, 3)}")
//    printSum(3, 6)

//    testIncrementX()
//    testMaxOf()

//    testParseInt()
//    testIsOperator()

//    testForLoop()
//    testWhileLoop()
//    testWhen()

//    testRange()
//    testRangeIterator()

//    testCollection()

    testShape()
}

fun printSum(a: Int, b: Int) {
    val a1: Int
    a1 = 10
    println("sum of $a and $b is ${a + b}, a1: $a1")
}

fun sum(a: Int, b: Int) = a + b


// var and val, const
const val PI = 3.14
var x = 0

fun incrementX() {
    x += 1
}

fun testIncrementX() {
    println("x = $x, PI = $PI")
    incrementX()
    println("incrementX()")
    println("x = $x, PI = $PI")
}


// if condition
fun maxOf(a: Int, b: Int) = if (a > b) a else b

fun testMaxOf() {
    println("max of 0 and 40 is ${maxOf(0, 40)}")
}


// null
fun parseInt(str: String): Int? {
    return str.toIntOrNull()
}

fun printProduct(arg1: String, arg2: String) {
    val x = parseInt(arg1)
    val y = parseInt(arg2)

    println("x: $x, y: $y")

    if (x != null && y != null) {
        println(x * y)
    } else {
        println("either '$arg1' or '$arg2' is not a number")
    }
}

fun testParseInt() {
    printProduct("6", "7")
    printProduct("a", "7")
    printProduct("a", "b")
}


// "is" operator
fun getStringLength(obj: Any): Int? {
    if (obj is String) {
        return obj.length
    }

    return null
}

fun testIsOperator() {
    fun printLength(obj: Any) {
        println("'$obj' string length is ${getStringLength(obj) ?: "... err, not a string"}")
    }

    printLength("Incomprehensibilities")
    printLength(1000)

}


// for loop
fun testForLoop() {
    val items = listOf("apple", "banana", "kiwifruit")
//    for (item in items) {
//        println(item)
//    }

    for (index in items.indices) {
        println("item at $index is ${items[index]}")
    }
}


// while loop
fun testWhileLoop() {
    val items = listOf("apple", "banana", "kiwifruit")
    var index = 0
    while (index < items.size) {
        println("item at $index is ${items[index]}")
        index++
    }
}


// when
fun describe(obj: Any): String =
    when (obj) {
        1 -> "One"
        "Hello" -> "Greeting"
        is Long -> "Long"
        !is String -> "Not a string"
        else -> "Unknown"
    }


fun testWhen() {
    println(describe(1))
    println(describe("Hello"))
    println(describe(1000L))
    println(describe(2))
    println(describe("other"))
}


// range test
fun testRange() {
    val x = 10
    val y = 9
    if (x in 1..y + 1) {
        println("fits in range")
    }

    val list = listOf("a", "b", "c")
    if (-1 !in 0..list.lastIndex) {
        println("-1 is out of range")
    }

    if (list.size !in list.indices) {
        println("list size is out of valid list indices range, too")
    }
}

fun testRangeIterator() {
    for (x in 1..5) print(x)
    println()

    for (x in 1..10 step 2) print(x)
    println()

    for (x in 9 downTo 0 step 3) print(x)
}


// collection
fun testCollection() {
    val items = listOf("apple", "banana", "kiwifruit")
    for (item in items) {
        println(item)
    }
    println()

    // in
    when {
        "orange" in items -> println("juicy")
        "apple" in items -> println("apple is fine too")
    }

    println()

    // lambada
    val fruit = listOf("banana", "avocado", "apple", "kiwifruit")
    fruit
        .asSequence()
        .filter { it.startsWith("a") }
        .sortedBy { it }
        .map { it.toUpperCase() }
        .toList()
        .forEach { println(it) }
}


// kotlin Class
abstract class Shape(val sides: List<Double>) {
    val perimeter: Double get() = sides.sum()
    abstract fun calculateArea(): Double
}

interface RectangleProperties {
    val isSquare: Boolean
}

class Rectangle(
    var height: Double,
    var length: Double
) : Shape(listOf(height, length, height, length)), RectangleProperties {
    override val isSquare: Boolean get() = length == height
    override fun calculateArea(): Double = height * length
}


class Triangle(
    var sideA: Double,
    var sideB: Double,
    var sideC: Double
) : Shape(listOf(sideA, sideB, sideC)) {
    override fun calculateArea(): Double {
        val s = perimeter / 2;
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC))
    }
}

fun testShape() {
    val rectangle = Rectangle(5.0, 2.0)
    val triangle = Triangle(3.0, 4.0, 5.0)
    println("Area of rectangle is ${rectangle.calculateArea()}, its perimeter is ${rectangle.perimeter}")
    println("Area of rectangle is ${triangle.calculateArea()}, its perimeter is ${triangle.perimeter}")
}