package chapter04

/**
 * Interface & 可见性修饰符
 * private, protected, internal, public
 */

// 接口以及属性，方法
interface MyInterface {
    val property01: String
    val prop: Int

    val propertyWithImplemention: String get() = "foo xxx"

    fun bar()
    fun foo() {
        println("property: $property01")
        println("prop: $prop")
    }
}

class Child(override val property01: String) : MyInterface {
    override val prop: Int = 100

    override fun bar() {
        println("this is Child's bar")
    }
}

fun testMyTestInterface() {
    // test interface
    var child = Child("child xxx")
    child.bar()
    child.foo()

    child.propertyWithImplemention.also { println(it) }
}


// 接口继承
interface Named {
    val name: String
}

interface Person : Named {
    val firstName: String
    val lastName: String

    override val name: String
        get() = "$firstName $lastName"
}

data class Employee(
    override val firstName: String,
    override val lastName: String
) : Person {
    override fun toString(): String {
        return "Employee: firstName: $firstName, lastName: $lastName"
    }
}

fun testPersonNamed() {
    val employee = Employee("john", "tan")
    employee.also { println(it) }
}


// override conflict
interface A {
    fun foo() {
        println("A")
    }

    fun bar()
}

interface B {
    fun foo() {
        println("B")
    }

    fun bar() {
        println("B-bar")
    }
}

class C : A {
    override fun bar() {
        println("C-bar")
    }
}

class D : A, B {
    override fun bar() {
        super<B>.bar()
    }

    override fun foo() {
        super<A>.foo()
        super<B>.foo()
    }
}

fun testConflictOverride() {
    val c = C()
    val d = D()

    c.foo()
    c.bar()

    d.foo()
    d.bar()
}


// 可见性修饰符
open class Outer {
    private val a = 1
    protected open val b = 2
    internal val c = 3
    val d = 4

    protected class Nested {
        public val e: Int = 5
    }
}

class Subclass : Outer() {

    override val b: Int = 5

    fun printProperty() {
        println()
    }
}

class Unrelated (o: Outer) {

    val outer = o

    fun test() {

    }
}


fun main(args: Array<String>) {
//    testMyTestInterface()

//    testPersonNamed()

    testConflictOverride()
}