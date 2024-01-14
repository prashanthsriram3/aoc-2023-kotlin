fun main() {
    data class Symbol(val rowIndex: Int, val colIndex: Int, val symbol: Char)
    data class EnginePart(val rowIndex: Int, val colIndex: Int, val value: Int)

    fun parseInput(input: List<String>): List<List<Char>> =
        input.map { it.trim().toList() }

    fun findEnginePart(row: List<Char>, rowIndex: Int, colStartIndex: Int): EnginePart {
        val colLeftIndex: Int = (colStartIndex downTo 0).firstOrNull { !row[it].isDigit() }?.let { it + 1 } ?: 0
        val colRightIndex: Int = (colStartIndex..<row.size).firstOrNull { !row[it].isDigit() } ?: row.size
        return EnginePart(rowIndex, colLeftIndex, row.subList(colLeftIndex, colRightIndex).joinToString("").toInt())
    }

    fun matchSymbolsWithEngineParts(grid: List<List<Char>>): Set<Pair<Symbol, EnginePart>> {
        val rowSize = grid.size
        val colSize = grid[0].size
        val symbolsEngineParts: MutableSet<Pair<Symbol, EnginePart>> = mutableSetOf()
        grid.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, c ->
                if (!c.isDigit() && c != '.') {
                    if (rowIndex > 0 && colIndex > 0 && grid[rowIndex - 1][colIndex - 1].isDigit()) {
                        symbolsEngineParts.add(Symbol(rowIndex, colIndex, c) to findEnginePart(grid[rowIndex - 1], rowIndex - 1, colIndex - 1))
                    }
                    if (rowIndex > 0 && colIndex < colSize - 1 && grid[rowIndex - 1][colIndex + 1].isDigit()) {
                        symbolsEngineParts.add(Symbol(rowIndex, colIndex, c) to findEnginePart(grid[rowIndex - 1], rowIndex - 1, colIndex + 1))
                    }
                    if (rowIndex < rowSize - 1 && colIndex > 0 && grid[rowIndex + 1][colIndex - 1].isDigit()) {
                        symbolsEngineParts.add(Symbol(rowIndex, colIndex, c) to findEnginePart(grid[rowIndex + 1], rowIndex + 1, colIndex - 1))
                    }
                    if (rowIndex < rowSize - 1 && colIndex < colSize - 1 && grid[rowIndex + 1][colIndex + 1].isDigit()) {
                        symbolsEngineParts.add(Symbol(rowIndex, colIndex, c) to findEnginePart(grid[rowIndex + 1], rowIndex + 1, colIndex + 1))
                    }
                    if (colIndex > 0 && grid[rowIndex][colIndex - 1].isDigit()) {
                        symbolsEngineParts.add(Symbol(rowIndex, colIndex, c) to findEnginePart(row, rowIndex, colIndex - 1))
                    }
                    if (rowIndex > 0 && grid[rowIndex - 1][colIndex].isDigit()) {
                        symbolsEngineParts.add(Symbol(rowIndex, colIndex, c) to findEnginePart(grid[rowIndex - 1], rowIndex - 1, colIndex))
                    }
                    if (colIndex < colSize - 1 && grid[rowIndex][colIndex + 1].isDigit()) {
                        symbolsEngineParts.add(Symbol(rowIndex, colIndex, c) to findEnginePart(row, rowIndex, colIndex + 1))
                    }
                    if (rowIndex < rowSize - 1 && grid[rowIndex + 1][colIndex].isDigit()) {
                        symbolsEngineParts.add(Symbol(rowIndex, colIndex, c) to findEnginePart(grid[rowIndex + 1], rowIndex + 1, colIndex))
                    }
                }
            }
        }
        return symbolsEngineParts
    }

    fun part1(input: List<String>): Int =
        matchSymbolsWithEngineParts(parseInput(input)).map { it.second }.toSet().sumOf { it.value }

    fun part2(input: List<String>): Int =
        matchSymbolsWithEngineParts(parseInput(input))
            .asSequence()
            .filter { it.first.symbol == '*' }
            .groupBy { it.first }
            .filter { it.value.size == 2 }
            .toList().sumOf { it.second[0].second.value * it.second[1].second.value }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
