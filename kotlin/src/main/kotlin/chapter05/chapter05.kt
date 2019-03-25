package chapter05


/**
 * 扩展， 扩展函数 & 扩展属性
 */

// 扩展函数
//fun MutableList<Int>.swap(index1: Int, index2: Int) {
//    val tmp = this[index1]
//    this[index1] = this[index2]
//    this[index2] = tmp
//}

fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}

fun testFunExtensions() {
    println("test int....")
    val l = mutableListOf(1, 2, 3)
    l.forEach { print("$it ") }
    println()

    l.swap(0, 1)
    l.forEach { print("$it ") }

    println("test String....")
    val stringList = mutableListOf("apple", "banana", "orange")
    stringList.forEach { print("$it ") }
    println()

    stringList.swap(0, 1)
    stringList.forEach { print("$it ") }
}






fun main(args: Array<String>) {
    testFunExtensions()
}