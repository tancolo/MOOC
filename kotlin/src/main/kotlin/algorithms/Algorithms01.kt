package algorithms

fun main() {
    val words = listOf(
        "com",
        "android",
        "external",
        "storage",
        "documents",
        "tree",
        "split",
        "axel",
        "can",
        "express",
        "download",
        "trash"
    )

    sortWordsWithKotlin(words)
}

/**
 * 有一个英文小写单词列表 List\，要求将其按首字母分组（key 为 ‘a’ - ‘z’），
 * 并且每个分组内的单词列表都是按升序排序，得到一个 Map\>。请尝试用 Kotlin 代码完成。
 *
 * @param words
 */
fun sortWordsWithKotlin(wordList: List<String>) {
    // 1. sort the list of each category
    // 2. classify the words with 'a' ~ 'z'
    // 3. print all the <Key, Value>

    wordList
        //.sortedBy { it }
        .groupBy { it[0] }
        .mapValues { it.value.sortedDescending() }
        .map { println(it) }
}


