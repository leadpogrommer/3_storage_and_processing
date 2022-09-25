package task_08

import sun.misc.Signal
import java.util.concurrent.CyclicBarrier

fun getComponent(i: Long): Double {
    val sign = if (i % 2 == 0L) 1.0 else -1.0
    return sign / (i * 2 + 1)
}

@Volatile
var isFinished = false

fun main(args: Array<String>) {
    val numThreads = Runtime.getRuntime().availableProcessors()


    Signal.handle(Signal("INT")){
        println("Got sigint")
        isFinished = true
    }

    val threadIterations = Array(numThreads){0L}
    var maxIteration = 0L
    val threadResults = Array(numThreads){0.0}

    val barrier = CyclicBarrier(numThreads){
        maxIteration = threadIterations.max()
        println("Maximum iteration is $maxIteration")
    }

    val threads = (0 until numThreads).map { i ->
        Thread {
            var result = 0.0
            var iteration = i.toLong()
            while (!isFinished){
                result += getComponent(iteration)
                iteration += numThreads
            }
            threadIterations[i] = iteration
            println("Thread $i \tpaused at iteration $iteration")
            barrier.await()
            while (iteration < maxIteration){
                result += getComponent(iteration)
                iteration += numThreads
            }
            println("Thread $i \tfinished at iteration $iteration")
            threadResults[i] = result
        }
    }

    threads.forEach { it.start() }
    threads.forEach { it.join() }
    val pi = threadResults.sum() * 4.0
    println("Pi = $pi")
}
