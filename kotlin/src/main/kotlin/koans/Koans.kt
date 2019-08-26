package koans

import java.util.*

// String literals

val textTrimMargin = """
    for (c in "foo")
        print(c)
""".trimMargin()

val textTrimIndent = """
    for (c in "foo")
        print(c)
""".trimIndent()


val text = """
    |Tell me and I forget.
    |Teach me and I remember.
    |Involve me and I learn.
    |(Benjamin Franklin)
    """.trimMargin()

val month = "(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)"

fun getPattern() = """\d{2}\.\d{2}\.\d{4}""".toRegex()

//fun main() {
//    //println(textTrimMargin)
//
//    //println("\n\n")
//    //println(textTrimIndent)
//
//    //println(text)
//
//    getPattern().matches("1113.02.2019").also { println(it) }
//}


fun sendMessageToClient(
    client: Client?, message: String?, mailer: Mailer
) {
    if (message == null) return

    client?.let {
        it.personalInfo?.let { it.email?.let { mailer.sendMessage(it, message) } }
    }
}

class Client(val personalInfo: PersonalInfo?)
class PersonalInfo(val email: String?)
interface Mailer {
    fun sendMessage(email: String, message: String)
}


fun eval(expr: Expr): Int =
    when (expr) {
        is Num -> expr.value
        is Sum -> eval(expr.left) + eval(expr.right)
        else -> throw IllegalArgumentException("Unknown expression")
    }

interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr


fun getList(): List<Int> {
    val arrayList = arrayListOf(1, 5, 2)
    Collections.sort(arrayList, {x, y -> x - y})
    return arrayList
}


fun getListObjectExpression(): List<Int> {
    val arrayList = arrayListOf(1, 5, 2)
    Collections.sort(arrayList, object: Comparator<Int> {
        override fun compare(x: Int, y: Int) = y - x
    })
    return arrayList
}

fun getListSorted(): List<Int> {
    return arrayListOf(1, 5, 2).sortedDescending()
}

fun main() {
    println(getListObjectExpression())
}