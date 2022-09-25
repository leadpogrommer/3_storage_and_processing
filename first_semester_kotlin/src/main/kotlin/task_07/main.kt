package task_07

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlin.math.ceil
import kotlin.math.floor


fun main(args: Array<String>){
    val parser = ArgParser("task_07")
    val numThreads by parser.argument(ArgType.Int, "threads")
    val numIterations by parser.argument(ArgType.Int, "iterations")
    parser.parse(args)

    val iterationsPerThread = ceil(numIterations.toDouble() / numThreads).toInt()
    println("Actual number of iterations: ${numThreads * iterationsPerThread}")
    val results = Array(numThreads) {0.0}

    val threads = (0 until numThreads).map{i ->
        Thread{
            results[i] = (i * iterationsPerThread until (i + 1) * iterationsPerThread).sumOf {
                val sign = if (it % 2 == 0) 1.0 else -1.0
                sign / (it * 2 + 1)
            }
        }
    }
    threads.forEach { it.start() }
    threads.forEach { it.join() }

    val pi = results.sum() * 4.0
    println("Pi = $pi")

}