package day_one.part_two
import java.io.File

val numberStings = listOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

fun calcOuterValue(str: String): Int {
    var outerValues = ""
    for (index in 0 .. str.length - 1) {
        if (str[index].isDigit()) {
            outerValues += str[index]
            break
        } else {
            for (num in numberStings) {
                if (num.first in str.substring(0..index)) {
                    outerValues += num.second
                    break
                }
            }
            if (outerValues.length == 1) break
        }
    }
    for (index in str.length - 1  downTo 0) {
        if (str[index].isDigit()) {
            outerValues += str[index]
            break
        } else {
            for (num in numberStings) {
                if (num.first in str.substring(index..str.length - 1)) {
                    outerValues += num.second
                    break
                }
            }
            if (outerValues.length == 2) break
        }
    }
    return if(outerValues.isEmpty()) 0 else outerValues.toInt()
}
fun calibrationDecipher(dataFile: File): Int? {
    if (!dataFile.exists()) return null
    var outerNumbSum = 0
    dataFile.forEachLine { line ->
        outerNumbSum += calcOuterValue(line)
    }
    return outerNumbSum
}

fun main() {
    println("calibration number: " + calibrationDecipher(File("src/main/kotlin/day_one/data/input.txt")))
}