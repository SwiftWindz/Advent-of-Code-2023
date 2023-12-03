package day_1.part_one
import java.io.File

/**
 * Part 1
 */
fun calcOuterValue(str: String): Int {
    var outerValues = ""
    for (index in 0 .. str.length - 1) {
        if (str[index].isDigit()) {
            outerValues += str[index]
            break
        }
    }
    for (index in str.length - 1  downTo 0) {
        if (str[index].isDigit()) {
            outerValues += str[index]
            break
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