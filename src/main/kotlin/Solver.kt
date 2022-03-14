import org.sat4j.core.Vec
import org.sat4j.core.VecInt
import org.sat4j.minisat.SolverFactory
import org.sat4j.specs.IProblem
import org.sat4j.specs.ISolver


class Solver {

    private val satSolver: ISolver = SolverFactory.newDefault()
    private val clauses: MutableList<List<Int>> = mutableListOf()

    fun solve(n: Int): String {
        clauses.clear()
        satSolver.newVar(n*n)
        satSolver.setExpectedNumberOfClauses(n*n*n*n)
        buildClauses(n)
        val arrangement = solveFormula() ?: return "For n=$n the problem is unsolvable :("
        return convertToBoard(n, arrangement)
    }

    private fun convertToPair(k: Int, n: Int): Pair<Int, Int>
        = Pair((k - 1) / n, (k - 1) % n)

    private fun buildClauses(n: Int) {
        exactlyOneInEveryRow(n)
        exactlyOneInEveryColumn(n)
        noMoreThanOneInEveryDiagonal(n)
    }

    private fun solveFormula(): List<Int>? {
        satSolver.addAllClauses(Vec(clauses.map { VecInt(it.toIntArray()) }.toTypedArray()))
        if (!(satSolver as IProblem).isSatisfiable)
            return null
        return (satSolver as IProblem).model().toList()
    }

    private fun convertToBoard(n: Int, arrangement: List<Int>): String {
        val sb = StringBuilder()
        arrangement.forEachIndexed { index, i ->
            if (index % n == 0) sb.append('\n')
            if (i > 0) sb.append('X') else sb.append('.')
        }
        return sb.toString()
    }

    private fun exactlyOneInEveryRow(n: Int) {
        for (row in 1..n) {
            val firstInRow = n * (row - 1) + 1
            val lastInRow = n * row
            val atLeastOne = (firstInRow..lastInRow).toList()
            clauses.add(atLeastOne)
            for (elem1 in firstInRow until lastInRow) {
                for (elem2 in (elem1 + 1)..lastInRow)
                    clauses.add(listOf(-elem1, -elem2))
            }
        }
    }

    private fun exactlyOneInEveryColumn(n: Int) {
        for (col in 1..n) {
            val lastInCol = col + (n - 1) * n
            val atLeastOne = (col..lastInCol step n).toList()
            clauses.add(atLeastOne)
            for (elem1 in col until lastInCol step n) {
                for (elem2 in (elem1 + n)..lastInCol step n)
                    clauses.add(listOf(-elem1, -elem2))
            }
        }
    }

    private fun noMoreThanOneInEveryDiagonal(n: Int) {
        (1..n).forEach { addAllPairsOnDiagonal(it, it, n - 1) }
        (2 * n..n * n step n).forEach { addAllPairsOnDiagonal(it, n - it / n + 1, n - 1) }
        (1..n).forEach { addAllPairsOnDiagonal(it, n - it + 1, n + 1) }
        (n + 1..n * n step n).forEach { addAllPairsOnDiagonal(it, n - it / n, n + 1) }
    }

    private fun addAllPairsOnDiagonal(start: Int, count: Int, step: Int) {
        val diagonalElements = List(count) { start + it * step }
        for (i in diagonalElements.indices) {
            for (j in i + 1 until diagonalElements.size)
                clauses.add(listOf(-diagonalElements[i], -diagonalElements[j]))
        }
    }

}
