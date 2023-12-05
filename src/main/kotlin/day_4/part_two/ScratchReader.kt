package day_4.part_two

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

fun sumNumbOfCards(cards: MutableSet<Card>, currentCardIndex: Int = 0, sum: Int = 0): Int {
    val currentCard = cards.elementAtOrNull(currentCardIndex) ?: return sum
    val numbOfChildren = currentCard
        .numbersOnCard
        .intersect(currentCard.winningNumbers)
        .size
    if (numbOfChildren == 0) return sum
    var sumOfChildren = sum
    for (childIndex in 1..numbOfChildren) sumOfChildren += sumNumbOfCards(cards, currentCardIndex + childIndex, sum + 1)
    return sumOfChildren
}

fun scratchCardSum(dataFile: File): Int? {
    if (!dataFile.exists()) return null
    val cards = parseCards(dataFile)
    return sumNumbOfCards(cards)
}

fun main() {
    println("Total points won on scratch cards: " + scratchCardSum(File("src/main/kotlin/day_4/data/input.txt")))
}
