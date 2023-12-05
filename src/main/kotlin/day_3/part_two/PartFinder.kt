package day_3.part_two

import java.io.File

data class NumberCoord (
    val number: Int,
    val yValue: Int,
    val xValues: Set<Int>
)

fun findValidParts(possibleParts: Set<NumberCoord>, partIdentifiers: MutableSet<Char>, lines: List<String>): Set<NumberCoord> {
    val validParts = mutableSetOf<NumberCoord>()
    val gears = mutableSetOf<Pair<Pair<Int,Int>, List<Int>>>()
    possibleParts.forEach { part ->
        part.xValues.forEach { xVal ->
            for (x in -1..1) {
                for (y in -1..1) {
                    if (part.yValue + y < 0 || part.yValue + y > lines.size - 1) continue
                    if (xVal + x < 0 || xVal + x > lines[part.yValue].length - 1) continue
                    if (lines[part.yValue + y][xVal + x] in partIdentifiers) {
                        if (lines[part.yValue + y][xVal + x] == '*')
                        else validParts.add(part)
                    }
                }
            }
        }
    }
    return validParts
}

fun parsePartConf(lines: List<String>, partIdentifiers: MutableSet<Char>): Set<NumberCoord> {
    val possibleParts = mutableSetOf<NumberCoord>()
    lines.forEachIndexed { yValue: Int, line: String ->
        var num = ""
        val xValues = mutableSetOf<Int>()
        line.forEachIndexed { xValue: Int, c: Char ->
            if (c.isDigit()) {
                num += c
                xValues.add(xValue)
            } else {
                if (num.isNotEmpty()) {
                    possibleParts.add(NumberCoord(num.toInt(), yValue, xValues.toSet()))
                    xValues.clear()
                    num = ""
                }
                if (c != '.') partIdentifiers.add(c)
            }
        }
        if (num.isNotEmpty()) {
            possibleParts.add(NumberCoord(num.toInt(), yValue, xValues.toSet()))
        }
    }
    return possibleParts
}

fun sumValidParts(dataFile: File): Int? {
    if (!dataFile.exists()) return null
    val lines = dataFile.readLines()
    val partIdentifiers = mutableSetOf<Char>()
    val possibleParts = parsePartConf(lines, partIdentifiers)
    return findValidParts(possibleParts, partIdentifiers, lines).sumOf { part ->
        part.number
    }
}

fun main() {
    println("Sum of valid part numbers: " + sumValidParts(File("src/main/kotlin/day_3/data/input")))
}