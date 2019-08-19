package algorithms
/**
 * 给定一段英文，找出每个单词使用的频率, 按照String字典顺序排序(忽略大小写)，
 * 并打印出所有单词及其频率的排序列表。
 * 如: “My name is …, I come from …, I am … years old!”
 */
fun main() {
    //val wordString  = "My name is ..., I come from  ..., I_m...  he's ... 10 years old! my"
    val wordString = "My name is …, I come from …, I am … years old!"

//    wordString
//            .toLowerCase() //*转小写
//            .replace(Regex("[^a-z0-9A-Z\\s]"), "") //*替换不是字母数字空格的字符为 ""
//            .split(" ") //*空格分词
//            .filter { it.matches(Regex("\\w+")) } //*过滤单词
//            .sortedBy { it } //排序
//            .groupBy { it } //分组
//            .toList() // 转换为列表
////            .sortedByDescending { it.second.size } // 按照词频高低排序
//            .forEach { (key, value) -> println("$key: ${value.size}") } //打印


//    wordString
//            .toLowerCase() //*转小写
//            .replace(Regex("[^a-z0-9A-Z\\s]"), "") //*替换不是字母数字空格的字符为 ""
//            .also { println("replaced: $it") }
//            .split(" ") //*空格分词
//            .also { println("splited: $it") }
//            .filter { it.matches(Regex("\\w+")) } //*过滤单词
//            .also { println("filtered: $it") }
//            .sortedBy { it } //排序
//            .also { println("sortedBy: $it") }
//            .groupBy { it } //分组
//            .also { println("groupBy: $it") }
//            .toList() // 转换为列表
//            .also { println("toList: $it") }
////            .sortedByDescending { it.second.size } // 按照词频高低排序
//            .apply { println("\n\n") }
//            .forEach { (key, value) -> println("$key: ${value.size}") } //打印



//    fun sendMoney(money: Coin, address: String, note: String?): Call<SendMoneyResponse> =
//            JSONObject()
//                    .apply {
//                        put("money", money.toLong())
//                        put("address", address)
//                        put("note", note)
//                    }.let {
//                        RequestBody.create(MediaType.parse("application/json"), it.toString())
//                    }.run {
//                        sendMoneyApi.sendMoney(this)
//                    }


    joinOptions(listOf("my", "name", "is"))
            .also { println(it) }

}

fun joinOptions(options: Collection<String>) = options.joinToString(prefix = "<<", postfix = ">>")