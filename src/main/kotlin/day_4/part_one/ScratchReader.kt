package day_4.part_one

import day_2.part_one.GameConfig
import day_2.part_one.isValidGame
import java.io.File

data class Card (
    val id: String,
    val winningNumbers: Set<Int>,
    val numbersOnCard: Set<Int>
)

fun scratchCardPointSum(dataFile: File): Int? {
    if (!dataFile.exists()) return null
    val cards = mutableListOf<Card>()
    dataFile.forEachLine { card ->
        val parsedCardInfo = card.split(":", "|")
        val sCard = Card(
            parsedCardInfo[0],
            parsedCardInfo[1]
                .trim()
                .split(" ")
                .filter { it.isNotBlank() }
                .map { it.toInt() }
                .toSet(),
            parsedCardInfo[2]
                .trim()
                .split(" ")
                .filter { it.isNotBlank() }
                .map { it.toInt() }
                .toSet()
        )
        cards.add(sCard)
    }
    return cards.sumOf { card ->
        var sum = 0
        card.winningNumbers.intersect(card.numbersOnCard).forEachIndexed { index, _ ->
            sum = if (index > 0) sum * 2 else 1
        }
        sum
    }
}

fun main() {
    println("Total points won on scratch cards: " + scratchCardPointSum(File("src/main/kotlin/day_4/data/input.txt")))
}
