package koans

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        return when {
            year != other.year -> year - other.year
            month != other.month -> month - other.month
            else -> dayOfMonth - other.dayOfMonth
        }
    }

}

fun compare(date1: MyDate, date2: MyDate) = date1 < date2


class DateRange(val start: MyDate, val endInclusive: MyDate) {
    operator fun contains(d: MyDate): Boolean {
        return start <= d && d <= endInclusive
    }
}


fun checkInRange(date: MyDate, first: MyDate, last: MyDate): Boolean {
    return date in DateRange(first, last)
}


fun main() {
    // case ONE
//    compare(MyDate(2019, 8, 22), MyDate(2019, 6, 21))
//        .also { println(it) }

    // case TWO
    checkInRange(
        MyDate(2010, 1, 20),
        MyDate(2010, 1, 20),
        MyDate(2019, 12, 22)
    )
        .also { println(it) }

}