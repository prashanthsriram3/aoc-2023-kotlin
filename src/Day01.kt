fun main() {
    val numMaps: Map<Int, Map<String, Int>> = mapOf(
        3 to mapOf("one" to 1, "two" to 2, "six" to 6),
        4 to mapOf("four" to 4, "five" to 5, "nine" to 9),
        5 to mapOf("three" to 3, "seven" to 7, "eight" to 8)
    )

    fun part1(input: List<String>): Int =
        input.sumOf { s: String ->
            listOf(s.first { it.isDigit() }, s.last { it.isDigit() }).joinToString("").toInt()
        }

    fun getNumList(s: String): List<Int> {
        val numList: MutableList<Int> = mutableListOf()
        s.forEachIndexed { index, c ->
            if (c.isDigit()) {
                numList.add(c.digitToInt())
            } else {
                run checkOffset@ {
                    listOf(3, 4, 5).forEach { offset ->
                        val endIndex = index + offset
                        if (endIndex <= s.length) {
                            val numMap: Map<String, Int> = numMaps.getValue(offset)
                            val key = s.substring(index, endIndex)
                            if (key in numMap) {
                                numList.add(numMap.getValue(key))
                                return@checkOffset
                            }
                        }
                    }
                }
            }
        }
        return numList
    }

    fun part2(input: List<String>): Int =
        input.sumOf { s: String ->
            val numList = getNumList(s)
            listOf(numList.first(), numList.last()).joinToString("").toInt()
        }

    // test if implementation meets criteria from the description, like:
    val testInputP1 = readInput("Day01p1_test")
    check(part1(testInputP1) == 142)

    val testInputP2 = readInput("Day01p2_test")
    check(part2(testInputP2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
