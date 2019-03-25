package chapter03

/**
 * Property
 */

// 声明属性
class Address {
    var name = ""
    var street: String = ""
    var city: String = ""
    var state: String = ""
    var zip: String = ""

    override fun toString(): String {
        return "Address: { name: $name, street: $street, city: $city, state: $state, zip: $zip }"
    }
}

fun copyAddress(address: Address): Address {
    val result = Address()
    result.name = address.name
    result.street = address.street
    result.city = address.city
    result.state = address.state
    result.zip = address.zip

    return result
}

fun testCopyAddress() {
    val origAddress = Address()
    origAddress.name = "Global Center"
    origAddress.street = "South first circle line"
    origAddress.city = "ChengDu"
    origAddress.state = "SiChuang"
    origAddress.zip = "620067"

    copyAddress(origAddress).also { println(it) }
}


// 自定义getter / setter方法
class CustomGetterString(string: String) {
    var initialized = 1
    var string = string

    private var size = string.length

    val isEmpty: Boolean
        get() = this.size == 0

}


fun testCustomString() {
    val customString = CustomGetterString("custom")
    customString.isEmpty.also { println(it) }

    val customString1 = CustomGetterString("")
    customString1.isEmpty.also { println(it) }
}


// 自定义setter方法
class CustomSetterString(string: String) {
    var stringUpperCase: String
        get() = this.toString()
        set(value) {
            // 解析字符串并赋值给其他属性
            println("value: $value")
            value.toUpperCase()
        }
}


fun testCustomSetterString() {
    val customString = CustomSetterString("ssss")
    customString.stringUpperCase = "upper case"
    println(customString.stringUpperCase)
}


// 幕后字段
class ShadowType() {
    var counter = 0
        set(value) {
            println("value: $value")
            if (value >= 0) field = value
        }
}

fun testShadowType() {
    var shadowType = ShadowType()
    shadowType.counter = 10

    shadowType.counter.also { println(it) }
}


// 编译期常量
const val SUBSYSTEM_DEPRECATED: String = "This subsystem is deprecated"

@Deprecated(SUBSYSTEM_DEPRECATED, ReplaceWith("println(\"test.... \")"))
fun foo() {
    //val test = "test"
    println("test.... ")
}


// lateinit
class TestSubject {

    fun method() {
        println("this is fun of method")
    }
}

class MyLateinitTest {
    lateinit var subject: TestSubject

    fun setup() {
        subject = TestSubject()
    }

    fun test() {
        subject.method()
    }
}



///////////// main //////////////
fun main(args: Array<String>) {
    //testCopyAddress()

//    testCustomString()

//    testCustomSetterString()

//    testShadowType()

//    foo()

    val lateinit = MyLateinitTest()
    lateinit.setup()
    lateinit.test()
}