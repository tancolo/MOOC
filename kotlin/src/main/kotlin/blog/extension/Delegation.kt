package blog.extension

// 1. sample case
//interface Base {
//    fun print()
//}
//
//class BaseImpl(val x: Int) : Base {
//    override fun print() {
//        println(x)
//    }
//}
//
//class Derived(b: Base) : Base by b
//
//fun main() {
//    val b = BaseImpl(10)
//    Derived(b).print()
//}


// 2. case 2
//interface Base {
//    fun printMessage()
//    fun printMessageLine()
//}
//
//class BaseImpl(val x: Int) : Base {
//    override fun printMessage() {
//        print(x)
//    }
//
//    override fun printMessageLine() {
//        println(x)
//    }
//}
//
//class Derived(b: Base) : Base by b {
//    override fun printMessage() {
//        print("abc")
//    }
//}
//
//fun main() {
//    val b = BaseImpl(10)
//    Derived(b).printMessage()
//    Derived(b).printMessageLine()
//}


// 3. case 3
// 以这种方式重写的成员不会在委托对象的成员中调用 ，委托对象的成员只能访问其自身对接口成员实现：
interface Base {
    //fun printMessage()
    //fun printMessageLine()

    val message: String
    fun print()
}

class BaseImpl(val x: Int) : Base {
//    override fun printMessage() {
//        print(x)
//    }
//    override fun printMessageLine() {
//        println(x)
//    }

    override val message = "BaseImpl: x = $x"
    override fun print() {
        println(message)
    }
}

class Derived(b: Base) : Base by b {
//    override fun printMessage() {
//        print("abc")
//    }

    override val message = "Message of Derived"
}

fun main() {
//    val b = BaseImpl(10)
//    Derived(b).printMessage()
//    Derived(b).printMessageLine()

    val b = BaseImpl(10)
    val derived = Derived(b)
    derived.print()
    println(derived.message)

}