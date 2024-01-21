import kotlin.math.round

fun main() {
    fun parseInput(input: List<String>): List<Pair<Long, Long>> {
        val times = input[0].trim().removePrefix("Time:").split(" ").filter { it.trim().isNotBlank() }.map { it.toLong() }
        val minDistances = input[1].trim().removePrefix("Distance:").split(" ").filter { it.trim().isNotBlank() }.map { it.toLong() }
        return times.zip(minDistances).toList()
    }

    fun countWays(time: Long, minDistance: Long): Long =
        (1..<time).first { (it * (time - it)) > minDistance }.let { (time - it) - it + 1 }

    fun part1(input: List<String>): Long =
        input.let { lines ->
            val times = lines[0].trim().removePrefix("Time:").split(" ")
                .filter { it.trim().isNotBlank() }.map { it.toLong() }
            val minDistances = lines[1].trim().removePrefix("Distance:").split(" ")
                .filter { it.trim().isNotBlank() }.map { it.toLong() }
            times.zip(minDistances).toList()
        }.fold(1) { acc, pair ->
            acc * countWays(pair.first, pair.second)
        }

    fun part2(input: List<String>): Long =
        input.let { lines ->
            val time = lines[0].trim().removePrefix("Time:").replace(" ", "").toLong()
            val minDistance = lines[1].trim().removePrefix("Distance:").replace(" ", "").toLong()
            countWays(time, minDistance)
        }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
