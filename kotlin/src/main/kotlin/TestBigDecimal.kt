

import java.math.BigDecimal

fun main() {
    val longToken = 50000000123L

    println(longToken.toBigDecimal().formatBigDecimalTokensToString())

    val stringAmount = "100000000.09999999"
    val bigDecimal = BigDecimal(stringAmount)
    val result = bigDecimal.formatBigDecimalTokensToLong()

    println("stringAmount: $stringAmount")
    println("bigDecimal: $bigDecimal")
    println("result: $result")


}

fun formatBigDecimalToString() {

    val longToken = 50000000123L

    val bigDecimalToken = longToken.toBigDecimal()

    val result = bigDecimalToken.divide(BigDecimal(100_000_000L))

    val stringToken = String.format("%.08f", result)


    println("longToken: $longToken,  bigDecimalToken: $bigDecimalToken,  result: $result, stringToken: $stringToken")


    val longToken11111 = 50000000123L
    val movetoLeft = BigDecimal(longToken11111).movePointLeft(8)

    println("\n\n movetoLeft: $movetoLeft")

}


fun BigDecimal.formatBigDecimalTokensToString(): String {
    val result = this.movePointLeft(8)
    return result.toString()
}

fun BigDecimal.formatBigDecimalTokensToLong(): Long {
    val result = this.movePointRight(8)
    return result.toLong()
}