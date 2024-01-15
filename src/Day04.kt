import kotlin.math.pow

fun main() {
    fun parseInput(input: List<String>): Map<Int, Int> =
        input.mapIndexed() { index, line ->
            val cardParts = line.trim().split(":")[1].trim().split("|")
            (index + 1) to (cardParts[1].trim().split(" ").mapNotNull { it.trim().toIntOrNull() }.toSet() intersect
                    cardParts[0].trim().split(" ").mapNotNull { it.trim().toIntOrNull() }.toSet()).count()
        }.toMap()

    fun part1(input: List<String>): Int =
        parseInput(input).values.sumOf {
            2.toDouble().pow(it - 1).toInt()
        }

    fun part2(input: List<String>): Int {
        val cardsMatch: Map<Int, Int> = parseInput(input).toSortedMap()
        val cardsCount: MutableMap<Int, Int> = cardsMatch.keys.associateWith { 1 }.toSortedMap().toMutableMap()
        cardsMatch.forEach { (key, value) ->
            ((key + 1)..(key + value)).forEach {
                cardsCount[it] = cardsCount[it]!! + cardsCount[key]!!
            }
        }
        return cardsCount.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
