package blog.kotlinbasic

fun sendMessageToClient(
    client: Client?, message: String?, mailer: Mailer
) {
    if (message == null) return

    client?.personalInfo?.email?.let { mailer.sendMessage(it, message) }
}

class Client(val personalInfo: PersonalInfo?)
class PersonalInfo(val email: String?)
interface Mailer {
    fun sendMessage(email: String, message: String)
}

//var nullableVariable: String? = "john.tan"
//println("TTTT===> $nullableVariable")


fun main() {
    //val filePath = listOf<String>("apple", "banana", "table", "111")
//    val filePath = listOf<String>()
//    filePath.map { it.length }
//        .reduce { accumulator, length -> accumulator + length }
//        .let { totalSize -> println("totalSize: $totalSize") }

    val a = 1
    val b = 10
    //val c = a > b ? a : b
}