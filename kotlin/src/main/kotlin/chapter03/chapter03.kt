package chapter03

/**
 * Class
 */


class Empty

// 主造函数
class Person(firstName: String) {
    init {
        print(firstName)
    }
}

class Persion01 constructor(firstName: String)

class InitOrderDemo(name: String) {
    val firstProperty = "First property: $name".also { println(it) }

    init {
        println("First initializer block that prints $name")
    }

    val secondProperty = "Second property: ${name.length}".also(::println)

    init {
        println("Second initializer block that prints ${name.length}")
    }
}


class Customer(name: String) {
    private val customerKey = name.toUpperCase()

    init {
        println(customerKey)
    }
}

class Person02(val firstName: String, val lastName: String, var age: Int) {

}

// 次构造函数
class Person03 {
    constructor(parent: Person03) {
        println(parent)
    }
}

class Person04(val name: String) {
    constructor(name: String, parent: Person04) : this(name)
}


class Constructors {
    init {
        println("Init block")
    }

    constructor(i: Int) {
        println("Constructor: $i")
    }
}


// private constructor
class DontCreateMe private constructor() {
}

class Customer01(private val customerName: String = "john.tan") {
    init {
        println("customer name is $customerName")
    }

    fun getName(): String {
        return customerName
    }
}


///inheritance
class Example : Any()

open class Base(p: Int)
class Derived(p: Int) : Base(p)


// override
open class Base01 {
    open fun v() {}
    fun nv() {}
}

class Derived01() : Base01() {
    override fun v() {}
}


open class AnotherDerived01() : Base01() {
    final override fun v() {
    }
}

open class Base02(val name: String) {
    init {
        println("Initializing Base")
    }

    open val size: Int = name.length.also { println("Initializing size in Base: $it") }
}

class Derived02(
    name: String,
    lastName: String
) : Base02(name.capitalize().also { println("Argument for Base: $it") }) {
    init {
        println("Initializing Derived")
    }

    override val size: Int =
        (super.size + lastName.length).also { println("Initializing size in Derived: $it") }
}

// 禁止再次覆盖
//open class AnotherDerived02() : AnotherDerived01() {
//    final override fun v() {
//    }
//}

///////////// main //////////////
fun main(args: Array<String>) {

    // InitOrderDemo("john.tan")

    // Customer
    // val customer = Customer("tan")

    // constructor
    // Constructors(1)

//    val customer = Customer01()
//    customer.getName().also { println(it) }

    println("Constructing Derived(\"hello\", \"world\")")
    val d = Derived02("hello", "world")
    println("d.size = ${d.size}")
}