package standardfunctions

import java.io.File


/**
 * (1)
 * run 优于 with
 * webview.setting?.run {
 * databaseEnabled = true
 * .... = ...
 * }
 *
 * with(webview.setting) {
 * this?.databaseEnabled = true
 * this?. ....
 * }
 *
 * (2) T.run is more superior over T.let  ???
 * run, use this
 * let, use it
 * 但是, let有其他好处
 * stringVariable?.let {
 * nonNullString ->
 * println("The non null string is $nonNullString")
 * }
 *
 *
 */
fun main(args: Array<String>) {
//    test()
//    testRunAndLet()
//    testLetAndAlso()
//    makeDir("test-also")
    makeDir01("test-also-01")
}

fun test() {
    var mood = "I am sad"

    run {
        val mood = "I am happy"
        println(mood)
    }

    println(mood)
}

fun testRunAndLet() {
    val stringVal = "The length of this String is ..."

    stringVal.run {
        println("The length of this String is $length")
    }
    stringVal.let {
        println("The length of this String is ${it.length}")
    }
}

fun testLetAndAlso() {
    val original = "abc"

    // .let
    original.let {
        println("111 The original String is $it")
        it.reversed()
//        println("222 The original String is $it")
    }.let {
        println("The reversed String is $it")
        it.length
    }.let {
        println("The length of the String is $it")
    }

    println("\n\n")
    // .also ==> wrong
    original.also {
        println("The original String is $it") // "abc"
        it.reversed() // even if we evolve it, it is useless
    }.also {
        println("The reverse String is $it") // "abc"
        it.length  // even if we evolve it, it is useless
    }.let {
        println("The length of the String is $it") // "abc"
    }

    println("\n\n")
    // .also ==> ok
    // Corrected for also (i.e. manipulate as original string
// Same value is sent in the chain
    original.also {
        println("The original String is $it") // "abc"
    }.also {
        println("The reverse String is ${it.reversed()}") // "cba"
    }.also {
        println("The length of the String is ${it.length}") // 3
    }
}


fun makeDir(path: String): File {
    val result = File(path)
    result.mkdirs()
    return result
}

fun makeDir01(path: String) = path.let { File(it) }.also { it.mkdirs() }