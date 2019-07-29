package algorithms

fun main() {

//    println("The infant rabbits per month: ${getInfantRabbitsPerMonth(9)}")


//    val initRabbit = 1
//    (1..12).sumBy { getInfantRabbitsPerMonth(it) }
//        .let { initRabbit + it }
//        .let { println(it) }


    1.let { initRabbit ->
        initRabbit + (1..12).sumBy { getInfantRabbitsPerMonth(it) }
    }
        .also { println("The infant rabbits per month: $it") }

}

/**
 * @param theMonth, means 1st, 2nd, 3rd, ... 12th, ...
 * return the number of infant rabbits after the xxth month.
 */
fun getInfantRabbitsPerMonth(theMonth: Int): Int {
    return when {
        theMonth <= 0 -> 0
        theMonth == 1 || theMonth == 2 -> 0
        theMonth == 3 -> 1
        else -> getInfantRabbitsPerMonth(theMonth - 1) + getInfantRabbitsPerMonth(theMonth - 3)
    }
}



