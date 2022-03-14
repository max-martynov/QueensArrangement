import kotlin.system.measureTimeMillis


fun main(args: Array<String>) {
    val n = readLine()?.toInt() ?: return
    println(Solver().solve(n))
}