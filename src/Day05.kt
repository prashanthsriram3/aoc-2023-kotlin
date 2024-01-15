fun main() {
    fun parseSection(input: List<String>, startIndex: Int): Pair<List<Pair<LongRange, LongRange>>, Int> {
        val dataList: MutableList<Pair<LongRange, LongRange>> = mutableListOf()
        var index = startIndex
        while (index < input.size && input[index].isNotBlank()) {
            val (destStart, srcStart, rangeLen) = input[index].split(" ").map { it.trim().toLong() }
            dataList.add((srcStart..<(srcStart + rangeLen)) to (destStart..<(destStart + rangeLen)))
            index += 1
        }
        return dataList.sortedBy { it.first.first } to index
    }

    fun parseRangesMap(input: List<String>): Map<String, List<Pair<LongRange, LongRange>>> {
        val rangesMap: MutableMap<String, List<Pair<LongRange, LongRange>>> = mutableMapOf()
        var section = parseSection(input, 3)
        rangesMap["seed-to-soil"] = section.first
        section = parseSection(input, section.second + 2)
        rangesMap["soil-to-fertilizer"] = section.first
        section = parseSection(input, section.second + 2)
        rangesMap["fertilizer-to-water"] = section.first
        section = parseSection(input, section.second + 2)
        rangesMap["water-to-light"] = section.first
        section = parseSection(input, section.second + 2)
        rangesMap["light-to-temperature"] = section.first
        section = parseSection(input, section.second + 2)
        rangesMap["temperature-to-humidity"] = section.first
        section = parseSection(input, section.second + 2)
        rangesMap["humidity-to-location"] = section.first
        return rangesMap
    }

    fun convert(ranges: List<Pair<LongRange, LongRange>>, srcList: List<Long>): List<Long> =
        srcList.map { src ->
            ranges.find { src in it.first }
                ?.let { range -> src - range.first.first + range.second.first }
                ?: src
        }

    fun seedsToLocations(seeds: List<Long>, rangesMap: Map<String, List<Pair<LongRange, LongRange>>>): List<Long> {
        val soils = convert(rangesMap.getValue("seed-to-soil"), seeds)
        val fertilizers = convert(rangesMap.getValue("soil-to-fertilizer"), soils)
        val waters = convert(rangesMap.getValue("fertilizer-to-water"), fertilizers)
        val lights = convert(rangesMap.getValue("water-to-light"), waters)
        val temperatures = convert(rangesMap.getValue("light-to-temperature"), lights)
        val humidity = convert(rangesMap.getValue("temperature-to-humidity"), temperatures)
        return convert(rangesMap.getValue("humidity-to-location"), humidity)
    }

    fun part1(input: List<String>): Long {
        val seeds = input[0].removePrefix("seeds: ").split(" ").map { it.trim().toLong() }
        val rangesMap = parseRangesMap(input)
        return seedsToLocations(seeds, rangesMap).min()
    }

    fun part2(input: List<String>): Long {
        val seedParts = input[0].trim().removePrefix("seeds: ").split(" ")
        val seedRanges: MutableList<LongRange> = mutableListOf()
        var index = 0
        while (index < seedParts.size - 1) {
            val start = seedParts[index].trim().toLong()
            val rangeLen = seedParts[index + 1].trim().toLong()
            seedRanges.add(start..<(start + rangeLen))
            index += 2
        }
        seedRanges.sortBy { it.first }
        val rangesMap = parseRangesMap(input)
        return seedRanges.flatMap { seedsToLocations(it.toList(), rangesMap) }.min()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
