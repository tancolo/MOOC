package blog.extension

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

//fun main() {
//    val hello = Hello()
//    println(hello.myLazyString)
//}
//
//class Hello {
//    val myLazyString: String by lazy { "Hello" }
//}


//延迟属性（lazy properties）: 其值只在首次访问时计算；
//可观察属性（observable properties）: 监听器会收到有关此属性变更的通知；
//把多个属性储存在一个映射（map）中，而不是每个存在单独的字段中。

/**
 *  1. 委托属性
 */
//class Example {
//    var p: String by Delegate()
//}
//
//class Delegate {
//    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
//        return "$thisRef, thank you for delegating '${property.name}' to me!"
//    }
//
//    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
//        println("$value has been assigned to '${property.name}' in $thisRef.")
//    }
//}
//
//fun main() {
//    val example = Example()
//    //example.p = "333"
//    println(example.p)
//}


/**
 * case 2 标准委托
 */

/**
 * 2-1 延迟属性 lazy
 */
//val lazyValue: String by lazy {
//    //LazyThreadSafetyMode.NONE
//    //println(Thread.currentThread().id)
//    println("computed!")
//    "Hello"
//}
//
//fun main() {
//    println(lazyValue)
//    println(lazyValue)
//}


/**
 * 2-2 可观察属性 Observable
 */
//class User {
//    var name: String by Delegates.observable("<no name>") { prop, old, new ->
//        println("${prop.name} ,  $old -> $new")
//    }
//}
//
//fun main() {
//    val user = User()
////    user.name = "first"
////    user.name = "second"
//
//    // vetoable
//    var max: Int by Delegates.vetoable(0) { property, oldValue, newValue ->
//        println("${property.name}, $oldValue, $newValue")
//        //newValue > oldValue
//        if (newValue > oldValue) true else
//            throw IllegalAccessException("New value must be larger than old value.")
//    }
//
//    println(max)
//
//    max = 10
//    println(max)
//
//    max = 5
//    println(max)
//
//}


/**
 * 2-3 把属性储存在映射中
 */
//class User(private val map: Map<String, Any?>) {
//    val name: String by map
//    val age: Int     by map
//}
//
//fun main() {
//    val user = User(
//        mapOf(
//            "name" to "John.tan",
//            "age" to 30
//        )
//    )
//
//    println(user.name)
//    println(user.age)
//
//}


/**
 * 2-4 局部委托属性（自 1.1 起）
 */




