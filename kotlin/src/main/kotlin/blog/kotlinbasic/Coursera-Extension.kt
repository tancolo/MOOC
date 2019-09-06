package blog.kotlinbasic

/**
 * 扩展是静态解析的
 */
//fun main() {
//    open class Shape
//
//    class Rectangle : Shape()
//
//    fun Shape.getName() = "Shape"
//
//    fun Rectangle.getName() = "Rectangle"
//
//    fun printClassName(s: Shape) {
//        println(s.getName())
//    }
//
//    printClassName(Rectangle())
//}

/**
 * 类集成实现getName()
 */
//open class Shape {
//    open fun getName() = "Shape"
//}
//
//class Rectangle : Shape() {
//    override fun getName() = "Rectangle"
//}

//fun main() {
//
//    fun Shape.getName() = "Shape for function extension"
//
//    fun Rectangle.getName() = "Rectangle for function extension"
//
//    fun printClassName(s: Shape) {
//        println(s.getName())
//    }
//
//    printClassName(Rectangle())
//
//    Shape().getName().also { println(it) }
//}

/**
 *扩展属性
 */

//val <T> List<T>.lastIndex: Int
//    get() = size - 1
//
//class House(private val name: String, val cost: Int = 1000) {
//    fun printHouse() = println("$name, $cost")
//}
//
//
//var number: Int = 0
//    get() = field
//    set(value) {
//        println("value: $value")
//        when {
//            value >= 0 -> field = value
//            else -> throw IllegalArgumentException("number can't < 0")
//        }
//    }
//
//const val PREFIX = "[JOHN===>]"
//
//class Person {
//    var firstName: String = ""
//        get() = field
//        set(value) {
//            field = value
//        }
//
//    var lastName: String = ""
//        get() {
//            println("get field: $field")
//            return when {
//                field.isNotEmpty() -> "${field.trim()}."
//                else -> field
//            }
//        }
//        set(value) {
//            println("set value: $value")
//            when {
//                value.length > 1 -> field = "$PREFIX$value"
//                else -> throw IllegalArgumentException("Last name too short!")
//            }
//        }
//
//}
//
//
//fun main() {
////    number = -10
//
//    val p = Person()
//    p.firstName = "A"
//    //println(p.firstName)
//
//    val p1 = Person()
//    p1.lastName = "D"
//    println(p1.lastName)
//
//}


/**
 * 幕后属性
 */
private var _table: Map<String, Int>? = null
public val table: Map<String, Int>
    get() {
        println("get table: $_table")
        if (_table == null) {
            _table = HashMap()
        }
        return _table ?: throw AssertionError("Set to null by another thread")
    }

fun main() {
    println("table 11 : $table")

    _table = mapOf(Pair("1", 1), Pair("2", 2))
    println("table 22: $table")
}
