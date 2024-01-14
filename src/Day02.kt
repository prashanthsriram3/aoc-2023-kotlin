fun main() {
    fun parseInput(input: List<String>): List<List<List<Pair<Int, String>>>> =
        input.mapIndexed { index: Int, s: String ->
            s.trim().removePrefix("Game ${index + 1}: ").split(";").map { games ->
                games.trim().split(",").map { game ->
                    val gameParts: List<String> = game.trim().split(" ")
                    Pair(gameParts[0].toInt(), gameParts[1])
                }
            }
        }

    fun part1(input: List<String>): Int =
        mapOf("red" to 12, "green" to 13, "blue" to 14).let { maxCubesMap ->
            parseInput(input).withIndex().filter {
                it.value.all { gameParts -> gameParts.all { gamePart -> maxCubesMap.getValue(gamePart.second) >= gamePart.first } }
            }.sumOf { it.index + 1 }
        }


    fun part2(input: List<String>): Int =
        parseInput(input).sumOf { games: List<List<Pair<Int, String>>> ->
            val maxCubeMap: MutableMap<String, Int> = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
            games.forEach { game ->
                game.forEach {
                    if (maxCubeMap.getValue(it.second) < it.first) {
                        maxCubeMap[it.second] = it.first
                    }
                }
            }
            maxCubeMap.values.fold(1) { accCubeCount: Int, cubeCount: Int -> accCubeCount * cubeCount  }
        }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
