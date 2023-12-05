package day_4.part_one

import day_2.part_one.GameConfig
import day_2.part_one.isValidGame
import day_4.part_two.Card
import java.io.File

data class Card (
    val id: Int,
    val winningNumbers: Set<Int>,
    val numbersOnCard: Set<Int>
)

fun parseCards(dataFile: File): MutableSet<Card> {

    fun extractNumbers(numbers: String): Set<Int> = numbers.trim()
        .split(" ")
        .filter { it.isNotBlank() }
        .map { it.toInt() }
        .toSet()

    val cards = mutableSetOf<Card>()
    dataFile.forEachLine { card ->
        val parsedCardInfo = card.split(":", "|")
        val nCard = Card(
            parsedCardInfo[0]
                .split(" ")
                .last
                .toInt(),
            extractNumbers(parsedCardInfo[1]),
            extractNumbers(parsedCardInfo[2])
        )
        cards.add(nCard)
    }
    return cards
}

fun scratchCardPointSum(dataFile: File): Int? {
    if (!dataFile.exists()) return null
    val cards = parseCards(dataFile)
    return cards.sumOf { card ->
        var sum = 0
        card.numbersOnCard
            .intersect(card.winningNumbers)
            .forEachIndexed { index, _ ->
                sum = if (index > 0) sum * 2 else 1
            }
        sum
    }
}

fun main() {
    println("Total points won on scratch cards: " + scratchCardPointSum(File("src/main/kotlin/day_4/data/input.txt")))
}
