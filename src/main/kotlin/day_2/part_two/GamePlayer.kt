package day_2.part_two

import java.io.File

/**
 * Contains information about a game conf
 */
data class GameConfig (
    val id: Int,
    val blocksPulled: List<String>
)

fun getMinimumBlocksPSum(game: GameConfig): Int {
    val regexPattern = """(\d+) (\w+)""".toRegex()
    val highestPulled = mutableMapOf<String, Int>()
    game.blocksPulled.forEach { blocks ->
        val matchResults = regexPattern.findAll(blocks)
        for (matchResult in matchResults) {
            val number = matchResult.groupValues[1].toInt()
            val color = matchResult.groupValues[2].trim()
            if ((highestPulled[color] ?: 0) < number) {
                highestPulled[color] = number
            }
        }
    }
    return highestPulled.values.reduce() { int1, int2 ->
            int1 * int2
        }

}

fun blockChecker(dataFile: File): Int? {
    if (!dataFile.exists()) return null
    var powerSum = 0
    dataFile.forEachLine { line ->
        val game = line.split(":", ";")
        val gameConf = GameConfig(game[0].split(" ")[1].toInt(), game.subList(1, game.size))
        powerSum += getMinimumBlocksPSum(gameConf)
    }
    return powerSum
}

fun main(args: Array<String>) {
    println("Power sum of minimum number of cubes : " + blockChecker(File("src/main/kotlin/day_two/data/input.txt")))
}
