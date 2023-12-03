package day_2.part_one

import java.io.File

/**
 * Contains information about a game conf
 */
data class GameConfig (
    val id: Int,
    val blocksPulled: List<String>
)

fun isValidGame(game: GameConfig, blockConf: Map<String, Int>): Boolean {
    val regexPattern = """(\d+) (\w+)""".toRegex()
    game.blocksPulled.forEach { blocks ->
        val matchResults = regexPattern.findAll(blocks)
        for (matchResult in matchResults) {
            val number = matchResult.groupValues[1].toInt()
            val color = matchResult.groupValues[2].trim()
            if (number > blockConf[color]!!) return false
        }
    }
    return true
}

fun validGameChecker(dataFile: File, blockConf: Map<String, Int>): Int? {
    if (!dataFile.exists()) return null
    var sumOfGameIds = 0
    dataFile.forEachLine { line ->
        val game = line.split(":", ";")
        val gameConf = GameConfig(game[0].split(" ")[1].toInt(), game.subList(1, game.size))
        if (isValidGame(gameConf, blockConf)) sumOfGameIds += gameConf.id
    }
    return sumOfGameIds
}

fun main(args: Array<String>) {
    if (args.size < 3) return
    val blockConf = mapOf(
        "red" to args[0].toInt(),
        "green" to args[1].toInt(),
        "blue" to args[2].toInt()
    )

    println("Blocks in bag: ")
    println("\tRed: ${blockConf["red"]}")
    println("\tGreen: ${blockConf["green"]}")
    println("\tBlue: ${blockConf["blue"]}")

    println("Sum of valid game IDs: " + validGameChecker(File("src/main/kotlin/day_two/data/input.txt"), blockConf))
}
